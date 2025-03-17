package com.konkuk.strhat.domain.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Job {
    STUDENT("대학생"),
    EMPLOYEE("직장인"),
    JOB_SEEKER("취준생");

    private final String description;
}
