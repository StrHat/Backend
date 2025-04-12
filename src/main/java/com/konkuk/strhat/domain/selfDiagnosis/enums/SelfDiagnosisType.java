package com.konkuk.strhat.domain.selfDiagnosis.enums;

import com.konkuk.strhat.domain.selfDiagnosis.exception.UnsupportedSelfDiagnosisTypeException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SelfDiagnosisType {
    PSS("Perceived Stress Scale"),  // 지각된 스트레스 척도
    SRI("Stress Response Inventory"), // 스트레스 반응 척도
    PHQ9("Patient Health Questionnaire-9"); // 우울증 선별 도구

    private final String description;

    public static SelfDiagnosisType toSelfDiagnosisType(String string) {
        try {
            return SelfDiagnosisType.valueOf(string.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new UnsupportedSelfDiagnosisTypeException();
        }
    }
}
