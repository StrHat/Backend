package com.konkuk.strhat.domain.diary.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
public class CheckDiaryResponse {
    private final boolean hasDiary;
    private final Integer emotion;
    private final String summary;

    public static CheckDiaryResponse of(boolean hasDiary, Integer emotion, String summary){
        return CheckDiaryResponse.builder()
                .hasDiary(hasDiary)
                .emotion(emotion)
                .summary(summary)
                .build();
    }
}