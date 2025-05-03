package com.konkuk.strhat.domain.stressScore.application;

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
import com.konkuk.strhat.domain.user.dao.UserRepository;
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
    private final StressScoreRepository stressScoreRepository;
    private final StressSummaryRepository stressSummaryRepository;
    private final UserRepository userRepository;
    private final DiaryRepository diaryRepository;

    @Transactional
    public DailyStressScoreResponse getOrCreateStressScore(LocalDate date, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(NotFoundUserException::new);

        StressScore score = stressScoreRepository.findByDiaryUserAndStressScoreDate(user, date)
                .orElseGet(() -> generateStressScore(user, date));

        return DailyStressScoreResponse.from(user.getNickname(), score);
    }

    @Transactional
    public WeeklyStressSummaryResponse getOrCreateWeeklyStressSummary(LocalDate date, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(NotFoundUserException::new);

        LocalDate weekStart = DateUtils.getStartOfWeek(date);
        LocalDate weekEnd = DateUtils.getEndOfWeek(date);

        // 1. 일주일치 일기 + 스트레스 점수를 한 번에 조회
        List<Diary> diaries = diaryRepository.findAllByUserAndDiaryDateBetweenOrderByDiaryDateAsc(user, weekStart, weekEnd);
        Map<Long, StressScore> stressScoreMap = stressScoreRepository.findAllByDiaryIn(diaries).stream()
                .collect(Collectors.toMap(s -> s.getDiary().getId(), Function.identity()));

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

        // 2. 요약 조회 or 생성
        StressSummary summary = stressSummaryRepository
                .findByUserAndWeekStartDate(user, weekStart)
                .orElseGet(() -> generateStressSummary(user, diaries, weekStart, weekEnd));

        return WeeklyStressSummaryResponse.of(
                user.getNickname(),
                summary.getContent(),
                stressLevels,
                emotionLevels,
                weekStart,
                weekEnd
        );
    }

    private StressScore generateStressScore(User user, LocalDate date) {
        Diary diary = user.getDiaries().stream()
                .filter(d -> d.getDiaryDate().equals(date))
                .findFirst()
                .orElseThrow(NotFoundDiaryException::new);

        // TODO 채팅 내역도 반영 로직 추가 필요

        // TODO AI 사용하는 로직으로 수정 필요
        StressScore mock = StressScore.builder()
                .score(6)
                .stressFactor("스트레스 요인 임시 데이터")
                .diary(diary)
                .build();

        if (stressScoreRepository.existsByDiary(diary)) {
            throw new DuplicateStressScoreException();
        }

        return stressScoreRepository.save(mock);
    }

    private StressSummary generateStressSummary(User user, List<Diary> diaries, LocalDate weekStart, LocalDate weekEnd) {
        // TODO 채팅 내역도 반영 로직 추가 필요

        // TODO AI 사용하는 로직으로 수정 필요
        StressSummary summary = StressSummary.builder()
                .content("스트레스 요약이 자동 생성되었습니다. 스트레스 완화에 힘써주세요.")
                .weekStartDate(weekStart)
                .weekEndDate(weekEnd)
                .build();
        user.addStressSummary(summary);

        return stressSummaryRepository.save(summary);
    }

}
