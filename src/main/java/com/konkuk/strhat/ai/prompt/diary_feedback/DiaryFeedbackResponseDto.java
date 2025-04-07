package com.konkuk.strhat.ai.prompt.diary_feedback;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

import static com.konkuk.strhat.ai.prompt.diary_feedback.DiaryFeedbackPrompt.DiaryFeedbackFieldNames.*;

@Getter
public class DiaryFeedbackResponseDto {

    @JsonProperty(POSITIVE)
    private List<String> positiveEmotionKeywords;

    @JsonProperty(NEGATIVE)
    private List<String> negativeEmotionKeywords;

    @JsonProperty(SUMMARY)
    private String diarySummary;

    @JsonProperty(SUGGESTION)
    private String stressReliefSuggestions;
}