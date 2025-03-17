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
@Table(name = "feedback")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id", updatable = false)
    private Long id;

    @Column(name = "diary_summary",length = 255, nullable = false)
    private String diarySummary;

    @Column(name = "positive_emotions",length = 255, nullable = false)
    private String positiveEmotions;

    @Column(name = "negative_emotions",length = 255, nullable = false)
    private String negativeEmotions;

    @Column(name = "stress_relief_suggestion",length = 255, nullable = false)
    private String stressReliefSuggestion;

    @Column(name = "feedback_date", nullable = false, unique = true)
    private LocalDate feedbackDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id", nullable = false)
    private Diary diary;

    @Builder
    public Feedback(String diarySummary,
                    String positiveEmotions,
                    String negativeEmotions,
                    String stressReliefSuggestion,
                    LocalDate feedbackDate,
                    Diary diary
    ) {
        this.diarySummary = diarySummary;
        this.positiveEmotions = positiveEmotions;
        this.negativeEmotions = negativeEmotions;
        this.stressReliefSuggestion = stressReliefSuggestion;
        this.feedbackDate = feedbackDate;
        this.diary = diary;
    }
}
