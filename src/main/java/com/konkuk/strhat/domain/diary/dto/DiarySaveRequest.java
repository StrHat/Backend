package com.konkuk.strhat.domain.diary.dto;

import com.konkuk.strhat.domain.diary.entity.Diary;
import com.konkuk.strhat.domain.user.entity.User;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiarySaveRequest {

    @NotNull
    private LocalDate date;

    @Min(1)
    @Max(5)
    private int emotion;

    @NotBlank
    @Size(min = 20, max = 1500)
    private String content;

    public Diary toDiaryEntity(User user) {
        Diary diary = Diary.builder()
                .content(this.content)
                .emotion(this.emotion)
                .diaryDate(this.date)
                .build();
        user.addDiary(diary);
        return diary;
    }
}
