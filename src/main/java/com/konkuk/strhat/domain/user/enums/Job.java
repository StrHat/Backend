package com.konkuk.strhat.domain.user.enums;

import com.konkuk.strhat.domain.user.exception.UnsupportedGenderTypeException;
import com.konkuk.strhat.domain.user.exception.UnsupportedJobTypeException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Job {
    STUDENT("대학생"),
    EMPLOYEE("직장인"),
    JOB_SEEKER("취준생");

    private final String description;

    public static Job from(String value) {
        return Arrays.stream(values())
                .filter(j -> j.getDescription().equals(value.trim()))
                .findFirst()
                .orElseThrow(UnsupportedJobTypeException::new);
    }
}
