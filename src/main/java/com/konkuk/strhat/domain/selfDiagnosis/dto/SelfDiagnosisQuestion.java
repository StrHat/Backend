package com.konkuk.strhat.domain.selfDiagnosis.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SelfDiagnosisQuestion {
    private int selfDiagnosisIndex;
    private String selfDiagnosisQuestion;

    public static SelfDiagnosisQuestion of(int selfDiagnosisIndex, String selfDiagnosisQuestion) {
        return SelfDiagnosisQuestion.builder()
                .selfDiagnosisIndex(selfDiagnosisIndex)
                .selfDiagnosisQuestion(selfDiagnosisQuestion)
                .build();
    }
}
