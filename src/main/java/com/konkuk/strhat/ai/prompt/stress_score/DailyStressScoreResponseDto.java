package com.konkuk.strhat.ai.prompt.stress_score;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.konkuk.strhat.domain.stressScore.exception.InvalidStressFactorLengthException;
import com.konkuk.strhat.domain.stressScore.exception.InvalidStressScoreResultException;
import lombok.Getter;

import static com.konkuk.strhat.ai.prompt.stress_score.DailyStressScorePrompt.DailyStressScoreFieldNames.FACTOR;
import static com.konkuk.strhat.ai.prompt.stress_score.DailyStressScorePrompt.DailyStressScoreFieldNames.SCORE;

@Getter
public class DailyStressScoreResponseDto {

    public static final int MIN_STRESS_SCORE = 1;
    public static final int MAX_STRESS_SCORE = 10;
    public static final int MAX_STRESS_FACTOR_LENGTH = 500;

    @JsonProperty(SCORE)
    private Integer stressScore;

    @JsonProperty(FACTOR)
    private String stressFactor;

    public void validateResult() {
        if (this.stressScore == null || this.stressFactor == null){
            throw new InvalidStressScoreResultException();
        }
        if (MAX_STRESS_FACTOR_LENGTH < this.stressFactor.length()) {
            throw new InvalidStressFactorLengthException(MAX_STRESS_FACTOR_LENGTH, this.stressFactor.length());
        }
    }
}