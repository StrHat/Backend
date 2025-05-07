package com.konkuk.strhat.ai.prompt.stress_score;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.konkuk.strhat.domain.stressScore.exception.InvalidStressSummaryResultException;
import com.konkuk.strhat.domain.stressScore.exception.InvalidStressSummaryLengthException;
import lombok.Getter;

import static com.konkuk.strhat.ai.prompt.stress_score.WeeklyStressSummaryPrompt.WeeklyStressSummaryFieldNames.SUMMARY;

@Getter
public class WeeklyStressSummaryResponseDto {

    public static final int MAX_SUMMARY_LENGTH = 700;

    @JsonProperty(SUMMARY)
    private String weeklyStressFactorSummary;

    public void validateResult() {
        if (this.weeklyStressFactorSummary == null){
            throw new InvalidStressSummaryResultException();
        }
        if (this.weeklyStressFactorSummary.length() > MAX_SUMMARY_LENGTH) {
            throw new InvalidStressSummaryLengthException(MAX_SUMMARY_LENGTH, this.weeklyStressFactorSummary.length());
        }
    }
}