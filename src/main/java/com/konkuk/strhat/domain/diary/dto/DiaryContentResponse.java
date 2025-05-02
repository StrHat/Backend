package com.konkuk.strhat.domain.diary.dto;

import com.konkuk.strhat.domain.diary.entity.Diary;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DiaryContentResponse {
    String content;

    public static DiaryContentResponse from(Diary diary) {
        return DiaryContentResponse.builder()
                .content(diary.getContent())
                .build();
    }
}
