package com.konkuk.strhat.domain.user.enums;

import com.konkuk.strhat.domain.user.exception.UnsupportedGenderTypeException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Gender {
    MALE("남자"),
    FEMALE("여자");

    private final String description;

    public static Gender from(String value) {
        return Arrays.stream(values())
                .filter(g -> g.getDescription().equals(value.trim()))
                .findFirst()
                .orElseThrow(UnsupportedGenderTypeException::new);
    }
}
