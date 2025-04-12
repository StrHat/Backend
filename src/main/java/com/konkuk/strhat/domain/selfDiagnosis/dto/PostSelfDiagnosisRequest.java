package com.konkuk.strhat.domain.selfDiagnosis.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostSelfDiagnosisRequest {
    @NotBlank
    private String type;

    @NotNull
    private Integer selfDiagnosisScore;
}
