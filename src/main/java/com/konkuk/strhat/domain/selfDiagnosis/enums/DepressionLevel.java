package com.konkuk.strhat.domain.selfDiagnosis.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DepressionLevel {
    MINIMAL("최소한의 우울 수준"),
    MILD("경미한 우울 수준"),
    MODERATE("중등도 우울 수준"),
    ABOVE_MODERATE("중등도 이상의 우울 수준"),
    SEVERE("심한 우울 수준");

    private final String description;
}
