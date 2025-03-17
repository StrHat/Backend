package com.konkuk.strhat.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // GLOBAL
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "G500", "서버 내부에 문제가 발생했습니다."),

    // USER
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "U404", "유저가 존재하지 않습니다");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
