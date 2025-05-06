package com.konkuk.strhat.ai.prompt.stress_score;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import static com.konkuk.strhat.ai.prompt.stress_score.DailyStressScorePrompt.DailyStressScoreFieldNames.FACTOR;
import static com.konkuk.strhat.ai.prompt.stress_score.DailyStressScorePrompt.DailyStressScoreFieldNames.SCORE;

@Getter
public class DailyStressScoreResponseDto {

    @JsonProperty(SCORE)
    private Integer stressScore;

    @JsonProperty(FACTOR)
    private String stressFactor;
}