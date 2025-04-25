package com.konkuk.strhat.domain.selfDiagnosis.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StressLevel {
    NORMAL("정상 스트레스 수준"),
    MILD("경미한 스트레스 수준"),
    MODERATE("중간 스트레스 수준"),
    HIGH("높은 스트레스 수준");

    private final String description;
}
