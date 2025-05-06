package com.konkuk.strhat.domain.stressScore.entity;

import com.konkuk.strhat.domain.diary.entity.Diary;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "stress_score")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StressScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stress_score_id", updatable = false)
    private Long id;

    @Column(name = "score", nullable = false)
    private Integer score;

    @Column(name = "stress_factor", length = 500, nullable = false)
    private String stressFactor;

    @Column(name = "stress_score_date", nullable = false)
    private LocalDate stressScoreDate;

    @Builder
    public StressScore(Integer score,
                       String stressFactor,
                       Diary diary
    ) {
        this.score = score;
        this.stressFactor = stressFactor;
        this.stressScoreDate = diary.getDiaryDate();
        diary.setStressScore(this);
    }
}
