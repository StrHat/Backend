package com.konkuk.strhat.domain.self_diagnosis.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SelfDiagnosisType {
    PSS("Perceived Stress Scale"),  // 지각된 스트레스 척도
    SRI("Stress Response Inventory"), // 스트레스 반응 척도
    PHQ9("Patient Health Questionnaire-9"); // 우울증 선별 도구

    private final String description;
}
