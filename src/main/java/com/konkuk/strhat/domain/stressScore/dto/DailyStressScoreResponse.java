package com.konkuk.strhat.domain.stressScore.dto;

import com.konkuk.strhat.domain.stressScore.entity.StressScore;
import com.konkuk.strhat.domain.stressScore.exception.ScoreOutOfRangeException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DailyStressScoreResponse {
    private final String nickname;
    private final Integer stressScore;
    private final String level;
    private final String analysis;
    private final LocalDate stressScoreDate;

    public static DailyStressScoreResponse from(String nickname, StressScore stressScore) {
        return DailyStressScoreResponse.builder()
                .nickname(nickname)
                .stressScore(stressScore.getScore())
                .level(getStressLevelText(stressScore.getScore()))
                .analysis(stressScore.getStressFactor())
                .stressScoreDate(stressScore.getStressScoreDate())
                .build();
    }

    private static String getStressLevelText(int score) {
        if (1 <= score && score <= 5) return "낮은 스트레스 수준";
        if (6 <= score && score <= 8) return "보통 스트레스 수준";
        if (9 <= score && score <= 10) return "높은 스트레스 수준";
        throw new ScoreOutOfRangeException("1 ~ 10 사이의 정수 이외의 점수는 처리할 수 없습니다. 입력된 점수: " + score);
    }
}
