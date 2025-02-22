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
    private int emotion;  // 1~5 사이의 숫자로 감정 저장

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "chat_available", nullable = false)
    private boolean chatAvailable = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public Diary(String content, int emotion, LocalDate date, boolean chatAvailable, User user) {
        this.content = content;
        this.emotion = emotion;
        this.date = date;
        this.chatAvailable = chatAvailable;
        this.user = user;
    }
}
