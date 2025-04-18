package com.konkuk.strhat.domain.diary.application;

import com.konkuk.strhat.ai.dto.GptReplyResult;
import com.konkuk.strhat.ai.prompt.diary_feedback.DiaryFeedbackPrompt;
import com.konkuk.strhat.ai.prompt.diary_feedback.DiaryFeedbackRequestDto;
import com.konkuk.strhat.ai.prompt.diary_feedback.DiaryFeedbackResponseDto;
import com.konkuk.strhat.ai.util.GptResponseParser;
import com.konkuk.strhat.ai.web_client.GptClient;
import com.konkuk.strhat.domain.diary.dao.DiaryRepository;
import com.konkuk.strhat.domain.diary.dao.FeedbackRepository;
import com.konkuk.strhat.domain.diary.entity.Diary;
import com.konkuk.strhat.domain.diary.entity.Feedback;
import com.konkuk.strhat.domain.diary.exception.FeedbackGenerateException;
import com.konkuk.strhat.domain.diary.exception.FeedbackSaveException;
import com.konkuk.strhat.domain.diary.exception.InvalidFeedbackEmotionFormatException;
import com.konkuk.strhat.domain.user.dao.UserRepository;
import com.konkuk.strhat.domain.user.dto.UserInfo;
import com.konkuk.strhat.domain.user.entity.User;
import com.konkuk.strhat.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final GptClient gptClient;
    private final GptResponseParser gptResponseParser;


    @Transactional
    public Feedback generateFeedbackAndSave(Diary diary) {
        Feedback feedback;

        try {
            // 1. 사용자 조회
            User user = diary.getUser();

            // 2. GPT API 요청에 필요한 DTO 구성
            UserInfo userInfo = UserInfo.from(user);
            DiaryFeedbackRequestDto request = new DiaryFeedbackRequestDto(userInfo, diary.getContent());

            // 3. Prompt 생성 및 요청
            DiaryFeedbackPrompt prompt = new DiaryFeedbackPrompt(request);
            GptReplyResult result = gptClient.chat(prompt);
            DiaryFeedbackResponseDto diaryFeedbackResponseDto = gptResponseParser.parse(result, DiaryFeedbackResponseDto.class);

            // 4. 피드백 객체 생성
            String positiveEmotionKeywords = diaryFeedbackResponseDto.getPositiveEmotionKeywords().toString();
            String negativeEmotionKeywords = diaryFeedbackResponseDto.getNegativeEmotionKeywords().toString();
            feedback = Feedback.builder()
                    .diarySummary(diaryFeedbackResponseDto.getDiarySummary())
                    .positiveEmotions(positiveEmotionKeywords.substring(1, positiveEmotionKeywords.length() - 1))
                    .negativeEmotions(negativeEmotionKeywords.substring(1, negativeEmotionKeywords.length() - 1))
                    .stressReliefSuggestion(diaryFeedbackResponseDto.getStressReliefSuggestions())
                    .diary(diary)
                    .build();
        } catch (Exception e) {
            throw new FeedbackGenerateException(e.getMessage());
        }

        // 감정 키워드 개수 검증
        if (feedback.getPositiveEmotionArray().length != 3 || feedback.getNegativeEmotionArray().length != 3) {
            throw new InvalidFeedbackEmotionFormatException(feedback.getPositiveEmotions(), feedback.getNegativeEmotions());
        }

        // AI 피드백 데이터 저장
        try {
            feedbackRepository.save(feedback);
        } catch (Exception e) {
            throw new FeedbackSaveException();
        }

        return feedback;
    }
}