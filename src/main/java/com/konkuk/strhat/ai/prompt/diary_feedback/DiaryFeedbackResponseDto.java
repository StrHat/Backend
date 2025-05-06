package com.konkuk.strhat.ai.prompt.diary_feedback;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.konkuk.strhat.domain.diary.exception.InvalidFeedbackEmotionFormatException;
import com.konkuk.strhat.domain.diary.exception.InvalidFeedbackResultException;
import lombok.Getter;

import java.util.List;

import static com.konkuk.strhat.ai.prompt.diary_feedback.DiaryFeedbackPrompt.DiaryFeedbackFieldNames.*;

@Getter
public class DiaryFeedbackResponseDto {

    public static final int KEYWORDS_SIZE = 3;
    public static final int MAX_DIARY_SUMMARY_LENGTH = 500;
    public static final int MAX_STRESS_RELIEF_SUGGESTIONS_LENGTH = 500;

    @JsonProperty(POSITIVE)
    private List<String> positiveEmotionKeywords;

    @JsonProperty(NEGATIVE)
    private List<String> negativeEmotionKeywords;

    @JsonProperty(SUMMARY)
    private String diarySummary;

    @JsonProperty(SUGGESTION)
    private String stressReliefSuggestions;

    public void validateResult() {
        if (this.positiveEmotionKeywords.size() != KEYWORDS_SIZE || this.negativeEmotionKeywords.size() != KEYWORDS_SIZE) {
            throw new InvalidFeedbackEmotionFormatException(this.positiveEmotionKeywords.toString(), this.negativeEmotionKeywords.toString());
        }

        if(this.diarySummary.length() > MAX_DIARY_SUMMARY_LENGTH){
            throw new InvalidFeedbackResultException();
        }

        if(this.stressReliefSuggestions.length() > MAX_STRESS_RELIEF_SUGGESTIONS_LENGTH){
            throw new InvalidFeedbackResultException();
        }
    }
}