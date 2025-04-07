package com.konkuk.strhat.domain.diary.dto;

import com.konkuk.strhat.domain.diary.entity.Feedback;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FeedbackResponse {
    String summary;
    String[] positiveKeywords;
    String[] negativeKeywords;
    String stressReliefSuggestions;

    public static FeedbackResponse of(Feedback feedback){
        return FeedbackResponse.builder()
                .summary(feedback.getDiarySummary())
                .positiveKeywords(feedback.getPositiveEmotionArray())
                .negativeKeywords(feedback.getNegativeEmotionArray())
                .stressReliefSuggestions(feedback.getStressReliefSuggestion())
                .build();
    }
}
