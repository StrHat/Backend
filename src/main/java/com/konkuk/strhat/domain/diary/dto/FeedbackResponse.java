package com.konkuk.strhat.domain.diary.dto;

import com.konkuk.strhat.domain.diary.entity.Feedback;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = PRIVATE)
public class FeedbackResponse {
    private final String summary;
    private final String[] positiveKeywords;
    private final String[] negativeKeywords;
    private final String stressReliefSuggestions;

    public static FeedbackResponse from(Feedback feedback){
        return FeedbackResponse.builder()
                .summary(feedback.getDiarySummary())
                .positiveKeywords(feedback.getPositiveEmotionArray())
                .negativeKeywords(feedback.getNegativeEmotionArray())
                .stressReliefSuggestions(feedback.getStressReliefSuggestion())
                .build();
    }
}
