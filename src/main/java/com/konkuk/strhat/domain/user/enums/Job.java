package com.konkuk.strhat.domain.user.enums;

import com.konkuk.strhat.domain.user.exception.UnsupportedJobTypeException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Job {
    STUDENT("대학생"),
    EMPLOYEE("직장인"),
    JOB_SEEKER("취준생");

    private final String description;

    public static Job toJob(String string) {
        try {
            return Job.valueOf(string.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new UnsupportedJobTypeException();
        }
    }
}
