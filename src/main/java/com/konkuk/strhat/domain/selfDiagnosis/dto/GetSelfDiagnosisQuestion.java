package com.konkuk.strhat.domain.selfDiagnosis.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetSelfDiagnosisQuestion {
    private int selfDiagnosisIndex;
    private String selfDiagnosisQuestion;

    public static GetSelfDiagnosisQuestion of(int selfDiagnosisIndex, String selfDiagnosisQuestion) {
        return GetSelfDiagnosisQuestion.builder()
                .selfDiagnosisIndex(selfDiagnosisIndex)
                .selfDiagnosisQuestion(selfDiagnosisQuestion)
                .build();
    }
}
