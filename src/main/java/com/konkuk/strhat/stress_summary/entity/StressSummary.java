package com.konkuk.strhat.stress_summary.entity;

import com.konkuk.strhat.user.entity.User;
import com.konkuk.strhat.global.entity.BaseCreatedEntity;
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

    @Column(name = "content", length = 255, nullable = false)
    private String content;

    @Column(name = "week_start_date", nullable = false)
    private LocalDate weekStartDate;

    @Column(name = "week_end_date", nullable = false)
    private LocalDate weekEndDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public StressSummary(String content, LocalDate weekStartDate, LocalDate weekEndDate, User user) {
        this.content = content;
        this.weekStartDate = weekStartDate;
        this.weekEndDate = weekEndDate;
        this.user = user;
    }
}
