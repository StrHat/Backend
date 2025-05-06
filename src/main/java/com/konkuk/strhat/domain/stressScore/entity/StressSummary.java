package com.konkuk.strhat.domain.stressScore.entity;

import com.konkuk.strhat.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "stress_summary")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StressSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stress_summary_id", updatable = false)
    private Long id;

    @Column(name = "content", length = 700, nullable = false)
    private String content;

    @Column(name = "week_start_date", nullable = false)
    private LocalDate weekStartDate;

    @Column(name = "week_end_date", nullable = false)
    private LocalDate weekEndDate;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public StressSummary(String content, LocalDate weekStartDate, LocalDate weekEndDate) {
        this.content = content;
        this.weekStartDate = weekStartDate;
        this.weekEndDate = weekEndDate;
    }
}
