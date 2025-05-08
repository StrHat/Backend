package com.konkuk.strhat.domain.diary.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = PRIVATE)
public class DiarySaveResponse {
    private final String summary;
    private final String[] positiveKeywords;
    private final String[] negativeKeywords;
    private final String stressReliefSuggestions;
    private final Long diaryId;

    public static DiarySaveResponse of(String summary, String[] positiveKeywords, String[] negativeKeywords, String stressReliefSuggestions, Long diaryId){
        return DiarySaveResponse.builder()
                .summary(summary)
                .positiveKeywords(positiveKeywords)
                .negativeKeywords(negativeKeywords)
                .stressReliefSuggestions(stressReliefSuggestions)
                .diaryId(diaryId)
                .build();
    }
}