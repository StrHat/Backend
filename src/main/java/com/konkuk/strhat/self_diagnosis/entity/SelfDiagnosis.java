package com.konkuk.strhat.self_diagnosis.entity;

import com.konkuk.strhat.global.entity.BaseCreatedEntity;
import com.konkuk.strhat.self_diagnosis.enums.SelfDiagnosisType;
import com.konkuk.strhat.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "self_diagnosis")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SelfDiagnosis extends BaseCreatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "self_diagnosis_id", updatable = false)
    private Long id;

    @Column(name = "score", nullable = false)
    private Integer score;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 10, nullable = false)
    private SelfDiagnosisType type;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public SelfDiagnosis(Integer score, SelfDiagnosisType type) {
        this.score = score;
        this.type = type;
    }
}
