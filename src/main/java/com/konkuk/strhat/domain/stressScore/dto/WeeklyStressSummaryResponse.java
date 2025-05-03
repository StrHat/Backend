package com.konkuk.strhat.domain.stressScore.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WeeklyStressSummaryResponse {

    private final String nickname;
    private final String weeklySummary;
    private final Integer[] stressLevels;   // 월~일 순
    private final Integer[] emotionLevels;  // 월~일 순
    private final LocalDate startDate;
    private final LocalDate endDate;

    public static WeeklyStressSummaryResponse of(
            String nickname,
            String weeklySummary,
            Integer[] stressLevels,
            Integer[] emotionLevels,
            LocalDate startDate,
            LocalDate endDate
    ) {
        return WeeklyStressSummaryResponse.builder()
                .nickname(nickname)
                .weeklySummary(weeklySummary)
                .stressLevels(stressLevels)
                .emotionLevels(emotionLevels)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
}
