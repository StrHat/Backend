package com.konkuk.strhat.domain.diary.application;

import com.konkuk.strhat.ai.dto.GptReplyResult;
import com.konkuk.strhat.ai.prompt.diary_feedback.DiaryFeedbackPrompt;
import com.konkuk.strhat.ai.prompt.diary_feedback.DiaryFeedbackRequestDto;
import com.konkuk.strhat.ai.prompt.diary_feedback.DiaryFeedbackResponseDto;
import com.konkuk.strhat.ai.util.GptResponseParser;
import com.konkuk.strhat.ai.web_client.GptClient;
import com.konkuk.strhat.domain.diary.entity.Diary;
import com.konkuk.strhat.domain.diary.entity.Feedback;
import com.konkuk.strhat.domain.diary.exception.FeedbackGenerateException;
import com.konkuk.strhat.domain.user.dto.UserInfoDto;
import com.konkuk.strhat.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AIFeedbackService {
    private final GptClient gptClient;
    private final GptResponseParser gptResponseParser;

    public Feedback generateFeedback(Diary diary) {
        User user = diary.getUser();

        log.info("[AIFeedbackService] 피드백 생성 시작, diaryId={}, userId={}", diary.getId(), user.getId());

        try {
            // 1. GPT API 요청에 필요한 DTO 구성
            log.info("[AIFeedbackService] GPT API 요청에 필요한 DTO 구성 시작, diaryId={}, userId={}", diary.getId(), user.getId());
            UserInfoDto userInfoDto = UserInfoDto.from(user);
            DiaryFeedbackRequestDto request = DiaryFeedbackRequestDto.of(userInfoDto, diary.getContent());

            // 2. Prompt 생성 및 요청
            log.info("[AIFeedbackService] 프롬프트 생성 시작, diaryId={}, userId={}", diary.getId(), user.getId());
            DiaryFeedbackPrompt prompt = new DiaryFeedbackPrompt(request);
            log.info("[AIFeedbackService] GPT 요청 시작, diaryId={}, userId={}", diary.getId(), user.getId());
            GptReplyResult result = gptClient.call(prompt);

            // 3. GPT 응답 파싱 및 유효성 검증
            log.info("[AIFeedbackService] GPT 응답 파싱 시작, diaryId={}, userId={}", diary.getId(), user.getId());
            DiaryFeedbackResponseDto diaryFeedbackResponseDto = gptResponseParser.parse(result, DiaryFeedbackResponseDto.class);
            log.info("[AIFeedbackService] GPT 응답 유효성 검증 시작, diaryId={}, userId={}", diary.getId(), user.getId());
            diaryFeedbackResponseDto.validateResult();

            // 4. 피드백 객체 생성 및 반환
            log.info("[AIFeedbackService] 피드백 객체 생성 시작, diaryId={}, userId={}", diary.getId(), user.getId());
            String positiveEmotionKeywords = diaryFeedbackResponseDto.getPositiveEmotionKeywords().toString();
            String negativeEmotionKeywords = diaryFeedbackResponseDto.getNegativeEmotionKeywords().toString();
            Feedback feedback = Feedback.builder()
                    .diarySummary(diaryFeedbackResponseDto.getDiarySummary())
                    .positiveEmotions(positiveEmotionKeywords.substring(1, positiveEmotionKeywords.length() - 1))
                    .negativeEmotions(negativeEmotionKeywords.substring(1, negativeEmotionKeywords.length() - 1))
                    .stressReliefSuggestion(diaryFeedbackResponseDto.getStressReliefSuggestions())
                    .diary(diary)
                    .build();
            log.info("[AIFeedbackService] 피드백 생성 완료, diaryId={}, userId={}", diary.getId(), user.getId());
            return feedback;

        } catch (Exception e) {
            log.error("[AIFeedbackService] 피드백 생성 실패, diaryId={}, userId={}", diary.getId(), user.getId());
            throw new FeedbackGenerateException(e.getMessage());
        }
    }
}