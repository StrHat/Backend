package com.konkuk.strhat.domain.diary.entity;

import com.konkuk.strhat.domain.stressScore.entity.StressScore;
import com.konkuk.strhat.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(
        name = "diary",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "diary_date"})
        }
)
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

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stress_score_id")
    private StressScore stressScore;

    @Builder
    public Diary(String content, Integer emotion, LocalDate diaryDate) {
        this.content = content;
        this.emotion = emotion;
        this.diaryDate = diaryDate;
    }
}
