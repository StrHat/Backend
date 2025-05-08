package com.konkuk.strhat.domain.diary.dto;

import com.konkuk.strhat.domain.diary.entity.Diary;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = PRIVATE)
public class DiaryContentResponse {
    private final String content;
    private final Long diaryId;

    public static DiaryContentResponse from(Diary diary) {
        return DiaryContentResponse.builder()
                .content(diary.getContent())
                .diaryId(diary.getId())
                .build();
    }
}
