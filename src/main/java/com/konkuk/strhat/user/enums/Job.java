package com.konkuk.strhat.user.enums;

import lombok.Getter;

@Getter
public enum Job {
    STUDENT("대학생"),
    EMPLOYEE("직장인"),
    JOB_SEEKER("취준생");

    private final String description;

    Job(String description) {
        this.description = description;
    }
}
