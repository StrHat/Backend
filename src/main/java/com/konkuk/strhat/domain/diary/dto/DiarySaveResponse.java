package com.konkuk.strhat.domain.diary.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DiarySaveResponse {
    private String summary;
    private String[] positiveKeywords;
    private String[] negativeKeywords;
    private String stressReliefSuggestions;
}