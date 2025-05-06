package com.konkuk.strhat.domain.stressScore.application;

import com.konkuk.strhat.ai.dto.GptReplyResult;
import com.konkuk.strhat.ai.prompt.stress_score.*;
import com.konkuk.strhat.ai.util.GptResponseParser;
import com.konkuk.strhat.ai.web_client.GptClient;
import com.konkuk.strhat.domain.diary.dao.DiaryRepository;
import com.konkuk.strhat.domain.diary.entity.Diary;
import com.konkuk.strhat.domain.diary.exception.NotFoundDiaryException;
import com.konkuk.strhat.domain.stressScore.dao.StressScoreRepository;
import com.konkuk.strhat.domain.stressScore.dao.StressSummaryRepository;
import com.konkuk.strhat.domain.stressScore.dto.DailyStressScoreResponse;
import com.konkuk.strhat.domain.stressScore.dto.WeeklyStressSummaryResponse;
import com.konkuk.strhat.domain.stressScore.entity.StressScore;
import com.konkuk.strhat.domain.stressScore.entity.StressSummary;
import com.konkuk.strhat.domain.stressScore.exception.DuplicateStressScoreException;
import com.konkuk.strhat.domain.stressScore.exception.DuplicateStressSummaryException;
import com.konkuk.strhat.domain.stressScore.exception.NoDiaryInWeekException;
import com.konkuk.strhat.domain.user.dao.UserRepository;
import com.konkuk.strhat.domain.user.dto.UserInfoDto;
import com.konkuk.strhat.domain.user.entity.User;
import com.konkuk.strhat.domain.user.exception.NotFoundUserException;
import com.konkuk.strhat.global.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class StressScoreService {
    private final GptClient gptClient;
    private final GptResponseParser gptResponseParser;

    private final StressScoreRepository stressScoreRepository;
    private final StressSummaryRepository stressSummaryRepository;
    private final UserRepository userRepository;
    private final DiaryRepository diaryRepository;

    @Transactional
    public DailyStressScoreResponse getOrCreateStressScore(LocalDate date, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(NotFoundUserException::new);

        StressScore score = stressScoreRepository.findByDiaryUserAndStressScoreDate(user, date)
                .orElseGet(() -> generateDailyStressScore(user, date));

        return DailyStressScoreResponse.from(user.getNickname(), score);
    }

    @Transactional
    public WeeklyStressSummaryResponse getOrCreateWeeklyStressSummary(LocalDate date, Long userId) {
        LocalDate weekStart = DateUtils.getStartOfWeek(date);
        LocalDate weekEnd = DateUtils.getEndOfWeek(date);

        User user = userRepository.findById(userId)
                .orElseThrow(NotFoundUserException::new);

        // 1. 일주일치 일기 조회
        List<Diary> diaries = diaryRepository.findAllByUserAndDiaryDateBetweenOrderByDiaryDateAsc(user, weekStart, weekEnd);
        if (diaries.isEmpty()) {
            throw new NoDiaryInWeekException(weekStart, weekEnd);
        }

        // 2. 일주일치 스트레스 점수 조회
        Map<Long, StressScore> stressScoreMap = stressScoreRepository.findAllByDiaryIn(diaries).stream()
                .collect(Collectors.toMap(s -> s.getDiary().getId(), Function.identity()));

        // 3. 감정 점수, 스트레스 점수 조회
        Integer[] stressLevels = new Integer[7];
        Integer[] emotionLevels = new Integer[7];
        for (Diary diary : diaries) {
            int index = diary.getDiaryDate().getDayOfWeek().getValue() - 1;
            emotionLevels[index] = diary.getEmotion();
            StressScore score = stressScoreMap.get(diary.getId());
            if (score != null) {
                stressLevels[index] = score.getScore();
            }
        }

        // 4. 요약 조회 or 생성
        StressSummary summary = stressSummaryRepository
                .findByUserAndWeekStartDate(user, weekStart)
                .orElseGet(() -> generateWeeklyStressSummary(user, diaries, weekStart, weekEnd));

        return WeeklyStressSummaryResponse.of(
                user.getNickname(),
                summary.getContent(),
                stressLevels,
                emotionLevels,
                weekStart,
                weekEnd
        );
    }

    private StressScore generateDailyStressScore(User user, LocalDate date) {
        // 1. 일기 조회
        Diary diary = user.getDiaries().stream()
                .filter(d -> d.getDiaryDate().equals(date))
                .findFirst()
                .orElseThrow(NotFoundDiaryException::new);
        if (stressScoreRepository.existsByDiary(diary)) {
            throw new DuplicateStressScoreException();
        }

        // 2. GPT API 요청에 필요한 DTO 구성
        DailyStressScoreRequestDto requestDto = DailyStressScoreRequestDto.of(
                UserInfoDto.from(user),
                diary.getContent()
        );

        // 3. Prompt 생성 및 요청
        // TODO 채팅 내역 반영 로직 추가 필요
        DailyStressScorePrompt prompt = new DailyStressScorePrompt(requestDto);
        GptReplyResult result = gptClient.chat(prompt);
        DailyStressScoreResponseDto dailyStressScoreResponseDto = gptResponseParser.parse(result, DailyStressScoreResponseDto.class);

        // 4. 응답 유효성 검증
        dailyStressScoreResponseDto.validateResult();

        // 5. StressScore 객체 생성 및 저장
        StressScore stressScore = StressScore.builder()
                .score(dailyStressScoreResponseDto.getStressScore())
                .stressFactor(dailyStressScoreResponseDto.getStressFactor())
                .diary(diary)
                .build();
        return stressScoreRepository.save(stressScore);
    }


    private StressSummary generateWeeklyStressSummary(User user, List<Diary> diaries, LocalDate weekStart, LocalDate weekEnd) {
        if (stressSummaryRepository.existsByUserAndWeekStartDate(user, weekStart)) {
            throw new DuplicateStressSummaryException();
        }

        // 1. GPT API 요청에 필요한 DTO 구성
        WeeklyStressSummaryRequestDto requestDto = WeeklyStressSummaryRequestDto.of(
                UserInfoDto.from(user),
                diaries.stream()
                        .map(diary -> String.format("[작성 날짜: %s] %s", diary.getDiaryDate(), diary.getContent()))
                        .toList()
        );

        // 3. Prompt 생성 및 요청
        // TODO 채팅 내역 반영 로직 추가 필요
        WeeklyStressSummaryPrompt prompt = new WeeklyStressSummaryPrompt(requestDto);
        GptReplyResult result = gptClient.chat(prompt);
        WeeklyStressSummaryResponseDto weeklyStressSummaryResponseDto = gptResponseParser.parse(result, WeeklyStressSummaryResponseDto.class);

        // 4. 응답 유효성 검증
        weeklyStressSummaryResponseDto.validateResult();

        // 5. StressSummary 객체 생성 및 저장
        StressSummary stressSummary = StressSummary.builder()
                .content(weeklyStressSummaryResponseDto.getWeeklyStressFactorSummary())
                .weekStartDate(weekStart)
                .weekEndDate(weekEnd)
                .build();
        user.addStressSummary(stressSummary);
        return stressSummaryRepository.save(stressSummary);
    }

}
