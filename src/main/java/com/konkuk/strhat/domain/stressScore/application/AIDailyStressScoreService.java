package com.konkuk.strhat.domain.stressScore.application;

import com.konkuk.strhat.ai.dto.GptReplyResult;
import com.konkuk.strhat.ai.prompt.stress_score.*;
import com.konkuk.strhat.ai.util.GptResponseParser;
import com.konkuk.strhat.ai.web_client.GptClient;
import com.konkuk.strhat.domain.diary.entity.Diary;
import com.konkuk.strhat.domain.diary.exception.NotFoundDiaryException;
import com.konkuk.strhat.domain.stressScore.entity.StressScore;
import com.konkuk.strhat.domain.stressScore.exception.DuplicateStressScoreException;
import com.konkuk.strhat.domain.stressScore.exception.UnknownStressScoreGenerateException;
import com.konkuk.strhat.domain.user.dto.UserInfoDto;
import com.konkuk.strhat.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class AIDailyStressScoreService {
    private final GptClient gptClient;
    private final GptResponseParser gptResponseParser;

    public StressScore generateDailyStressScore(LocalDate date, User user) {
        Diary diary = user.getDiaries().stream()
                .filter(d -> d.getDiaryDate().equals(date))
                .findFirst()
                .orElseThrow(NotFoundDiaryException::new);
        if (diary.getStressScore() != null) {
            throw new DuplicateStressScoreException();
        }

        log.info("[AIDailyStressScoreService] 스트레스 점수 측정 시작, diaryId={}, userId={}", diary.getId(), user.getId());

        try {
            // 1. GPT API 요청에 필요한 DTO 구성
            log.info("[AIDailyStressScoreService] GPT API 요청에 필요한 DTO 구성 시작, diaryId={}, userId={}", diary.getId(), user.getId());
            DailyStressScoreRequestDto requestDto = DailyStressScoreRequestDto.of(
                    UserInfoDto.from(user),
                    diary.getContent()
            );

            // 2. Prompt 생성 및 요청
            // TODO 채팅 내역 반영 로직 추가 필요
            log.info("[AIDailyStressScoreService] 프롬프트 생성 시작, diaryId={}, userId={}", diary.getId(), user.getId());
            DailyStressScorePrompt prompt = new DailyStressScorePrompt(requestDto);
            log.info("[AIDailyStressScoreService] GPT 요청 시작, diaryId={}, userId={}", diary.getId(), user.getId());
            GptReplyResult result = gptClient.call(prompt);


            // 3. GPT 응답 파싱 및 유효성 검증
            log.info("[AIDailyStressScoreService] GPT 응답 파싱 시작, diaryId={}, userId={}", diary.getId(), user.getId());
            DailyStressScoreResponseDto dailyStressScoreResponseDto = gptResponseParser.parse(result, DailyStressScoreResponseDto.class);
            log.info("[AIDailyStressScoreService] GPT 응답 유효성 검증 시작, diaryId={}, userId={}", diary.getId(), user.getId());
            dailyStressScoreResponseDto.validateResult();

            // 4. StressScore 객체 생성 및 반환
            log.info("[AIDailyStressScoreService] StressScore 객체 생성 시작, diaryId={}, userId={}", diary.getId(), user.getId());
            StressScore stressScore = StressScore.builder()
                    .score(dailyStressScoreResponseDto.getStressScore())
                    .stressFactor(dailyStressScoreResponseDto.getStressFactor())
                    .diary(diary)
                    .build();
            log.info("[AIDailyStressScoreService] StressScore 객체 생성 완료, diaryId={}, userId={}", diary.getId(), user.getId());
            return stressScore;

        } catch (Exception e) {
            log.error("[AIDailyStressScoreService] 스트레스 점수 측정 실패, diaryId={}, userId={}", diary.getId(), user.getId());
            throw new UnknownStressScoreGenerateException(e.getMessage());
        }
    }
}
