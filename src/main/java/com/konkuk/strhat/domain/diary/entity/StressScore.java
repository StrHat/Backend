package com.konkuk.strhat.domain.diary.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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

    @Column(name = "stress_factor", length = 255, nullable = false)
    private String stressFactor;

    @Column(name = "stress_score_date", nullable = false, unique = true)
    private LocalDate stressScoreDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id", nullable = false)
    private Diary diary;

    @Builder
    public StressScore(Integer score,
                       String stressFactor,
                       LocalDate stressScoreDate,
                       Diary diary
    ) {
        this.score = score;
        this.stressFactor = stressFactor;
        this.stressScoreDate = stressScoreDate;
        this.diary = diary;
    }
}
