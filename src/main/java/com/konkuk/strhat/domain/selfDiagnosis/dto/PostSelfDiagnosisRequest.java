package com.konkuk.strhat.domain.selfDiagnosis.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostSelfDiagnosisRequest {
    private String type;
    private Integer selfDiagnosisScore;
}
