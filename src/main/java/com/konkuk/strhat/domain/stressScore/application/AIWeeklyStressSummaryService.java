package com.konkuk.strhat.domain.stressScore.application;

import com.konkuk.strhat.ai.dto.GptReplyResult;
import com.konkuk.strhat.ai.prompt.stress_score.*;
import com.konkuk.strhat.ai.util.GptResponseParser;
import com.konkuk.strhat.ai.web_client.GptClient;
import com.konkuk.strhat.domain.diary.dao.DiaryRepository;
import com.konkuk.strhat.domain.diary.entity.Diary;
import com.konkuk.strhat.domain.stressScore.dao.StressScoreRepository;
import com.konkuk.strhat.domain.stressScore.dao.StressSummaryRepository;
import com.konkuk.strhat.domain.stressScore.dto.WeeklyStressSummaryResponse;
import com.konkuk.strhat.domain.stressScore.entity.StressScore;
import com.konkuk.strhat.domain.stressScore.entity.StressSummary;
import com.konkuk.strhat.domain.stressScore.exception.DuplicateStressSummaryException;
import com.konkuk.strhat.domain.stressScore.exception.NoDiaryInWeekException;
import com.konkuk.strhat.domain.stressScore.exception.UnknownStressScoreGenerateException;
import com.konkuk.strhat.domain.user.dao.UserRepository;
import com.konkuk.strhat.domain.user.dto.UserInfoDto;
import com.konkuk.strhat.domain.user.entity.User;
import com.konkuk.strhat.domain.user.exception.NotFoundUserException;
import com.konkuk.strhat.global.util.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AIWeeklyStressSummaryService {
    private final GptClient gptClient;
    private final GptResponseParser gptResponseParser;

    private final StressSummaryRepository stressSummaryRepository;
    private final DiaryRepository diaryRepository;

    public StressSummary generateWeeklyStressSummary(User user, LocalDate weekStartDate, LocalDate weekEndDate) {
        if (stressSummaryRepository.existsByUserAndWeekStartDate(user, weekStartDate)) {
            throw new DuplicateStressSummaryException();
        }
        List<Diary> diaries = diaryRepository.findAllByUserAndDiaryDateBetweenOrderByDiaryDateAsc(user, weekStartDate, weekEndDate);
        if (diaries.isEmpty()) {
            throw new NoDiaryInWeekException(weekStartDate, weekEndDate);
        }

        log.info("[AIWeeklyStressSummaryService] 주간 스트레스 원인 분석 시작, weekStartDate={}, weekEndDate={}, userId={}", weekStartDate, weekEndDate, user.getId());

        try {
            // 1. GPT API 요청에 필요한 DTO 구성
            log.info("[AIWeeklyStressSummaryService] GPT API 요청에 필요한 DTO 구성 시작, weekStartDate={}, weekEndDate={}, userId={}", weekStartDate, weekEndDate, user.getId());
            WeeklyStressSummaryRequestDto requestDto = WeeklyStressSummaryRequestDto.of(
                    UserInfoDto.from(user),
                    diaries.stream()
                            .map(diary -> String.format("[작성 날짜: %s] %s", diary.getDiaryDate(), diary.getContent()))
                            .toList()
            );

            // 2. Prompt 생성 및 요청
            // TODO 채팅 내역 반영 로직 추가 필요
            log.info("[AIWeeklyStressSummaryService] 프롬프트 생성 시작, weekStartDate={}, weekEndDate={}, userId={}", weekStartDate, weekEndDate, user.getId());
            WeeklyStressSummaryPrompt prompt = new WeeklyStressSummaryPrompt(requestDto);
            log.info("[AIWeeklyStressSummaryService] GPT 요청 시작, weekStartDate={}, weekEndDate={}, userId={}", weekStartDate, weekEndDate, user.getId());
            GptReplyResult result = gptClient.call(prompt);

            // 3. GPT 응답 파싱 및 유효성 검증
            log.info("[AIWeeklyStressSummaryService] GPT 응답 파싱 시작, weekStartDate={}, weekEndDate={}, userId={}", weekStartDate, weekEndDate, user.getId());
            WeeklyStressSummaryResponseDto weeklyStressSummaryResponseDto = gptResponseParser.parse(result, WeeklyStressSummaryResponseDto.class);
            log.info("[AIWeeklyStressSummaryService] GPT 응답 유효성 검증 시작, weekStartDate={}, weekEndDate={}, userId={}", weekStartDate, weekEndDate, user.getId());
            weeklyStressSummaryResponseDto.validateResult();

            // 4. StressSummary 객체 생성
            log.info("[AIWeeklyStressSummaryService] StressSummary 객체 생성 시작, weekStartDate={}, weekEndDate={}, userId={}", weekStartDate, weekEndDate, user.getId());
            StressSummary stressSummary = StressSummary.builder()
                    .content(weeklyStressSummaryResponseDto.getWeeklyStressFactorSummary())
                    .weekStartDate(weekStartDate)
                    .weekEndDate(weekEndDate)
                    .build();
            log.info("[AIWeeklyStressSummaryService] StressSummary 객체 생성 완료, weekStartDate={}, weekEndDate={}, userId={}", weekStartDate, weekEndDate, user.getId());
            return stressSummary;
        } catch (Exception e) {
            log.error("[AIWeeklyStressSummaryService] 주간 스트레스 원인 분석 실패, weekStartDate={}, weekEndDate={}, userId={}", weekStartDate, weekEndDate, user.getId());
            throw new UnknownStressScoreGenerateException(e.getMessage());
        }
    }
}
