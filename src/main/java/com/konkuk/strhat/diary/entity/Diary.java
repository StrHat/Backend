package com.konkuk.strhat.diary.entity;

import com.konkuk.strhat.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "diary")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id", updatable = false)
    private Long id;

    @Column(name = "content", length = 1500, nullable = false)
    private String content;

    @Column(name = "emotion", nullable = false)
    private Integer emotion;

    @Column(name = "diary_date", nullable = false)
    private LocalDate diaryDate;

    @Column(name = "chat_available", nullable = false)
    private Boolean chatAvailable = true;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public Diary(String content, Integer emotion, LocalDate diaryDate, Boolean chatAvailable) {
        this.content = content;
        this.emotion = emotion;
        this.diaryDate = diaryDate;
        this.chatAvailable = chatAvailable;
    }
}
