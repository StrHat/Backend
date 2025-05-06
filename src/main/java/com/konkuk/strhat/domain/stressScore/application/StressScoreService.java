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
import com.konkuk.strhat.domain.stressScore.exception.NoDiaryInWeekException;
import com.konkuk.strhat.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StressScoreService {
    private final StressScoreRepository stressScoreRepository;
    private final StressSummaryRepository stressSummaryRepository;
    private final DiaryRepository diaryRepository;

    @Transactional(readOnly = true)
    public Optional<DailyStressScoreResponse> readDailyStressScore(LocalDate date, User user) {
        Diary diary = diaryRepository.findByDiaryDateAndUser(date, user)
                .orElseThrow(NotFoundDiaryException::new);
        StressScore stressScroe = diary.getStressScore();
        if(stressScroe != null){
            return Optional.ofNullable(DailyStressScoreResponse.from(user.getNickname(), stressScroe));
        }else{
            return Optional.empty();
        }
    }

    @Transactional
    public DailyStressScoreResponse saveDailyStressScore(StressScore stressScore, User user) {
        return DailyStressScoreResponse.from(user.getNickname(), stressScoreRepository.save(stressScore));
    }

    @Transactional(readOnly = true)
    public Optional<WeeklyStressSummaryResponse> readStressSummary(LocalDate weekStartDate, LocalDate weekEndDate, User user) {
        return stressSummaryRepository.findByUserAndWeekStartDate(user, weekStartDate)
                .map(summary -> buildWeeklyStressSummaryResponse(user, weekStartDate, weekEndDate, summary.getContent()));
    }

    @Transactional
    public WeeklyStressSummaryResponse saveStressSummary(StressSummary summary, User user) {
        user.addStressSummary(summary);
        stressSummaryRepository.save(summary);

        return buildWeeklyStressSummaryResponse(user, summary.getWeekStartDate(), summary.getWeekEndDate(), summary.getContent());
    }

    private WeeklyStressSummaryResponse buildWeeklyStressSummaryResponse(
            User user, LocalDate weekStartDate, LocalDate weekEndDate, String summaryContent) {

        List<Diary> diaries = diaryRepository.findAllByUserAndDiaryDateBetweenOrderByDiaryDateAsc(user, weekStartDate, weekEndDate);
        if (diaries.isEmpty()) {
            throw new NoDiaryInWeekException(weekStartDate, weekEndDate);
        }

        Integer[] emotionLevels = new Integer[7];
        Integer[] stressLevels = new Integer[7];

        for (Diary diary : diaries) {
            int index = diary.getDiaryDate().getDayOfWeek().getValue() - 1;

            emotionLevels[index] = diary.getEmotion();
            if (diary.getStressScore() != null) {
                stressLevels[index] = diary.getStressScore().getScore();
            }
        }

        return WeeklyStressSummaryResponse.of(
                user.getNickname(),
                summaryContent,
                stressLevels,
                emotionLevels,
                weekStartDate,
                weekEndDate
        );
    }

}
