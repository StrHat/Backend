package com.konkuk.strhat.domain.diary.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CheckDiaryResponse {
    private final boolean hasDiary;
    private final Integer emotion;
    private final String summary;
}