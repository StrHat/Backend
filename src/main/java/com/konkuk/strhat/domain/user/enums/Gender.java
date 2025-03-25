package com.konkuk.strhat.domain.user.enums;

import com.konkuk.strhat.domain.user.exception.UnsupportedGenderTypeException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender {
    MALE("남자"),
    FEMALE("여자");

    private final String description;

    public static Gender toGender(String string) {
        try {
            return Gender.valueOf(string.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new UnsupportedGenderTypeException();
        }
    }
}
