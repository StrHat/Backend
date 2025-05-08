package com.konkuk.strhat.domain.chat.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ChatMode {
    EMPATHY_MODE("공감 모드"),
    SOLUTION_MODE("해결책 모드");

    private final String description;
}
