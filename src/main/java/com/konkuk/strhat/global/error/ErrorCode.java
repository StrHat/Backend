package com.konkuk.strhat.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // GLOBAL
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "G500", "서버 내부에 문제가 발생했습니다."),

    // USER
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "U404", "유저가 존재하지 않습니다"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "U405", "허용되지 않는 메서드입니다"),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "U409", "이미 존재하는 이메일입니다."),
    UNSUPPORTED_GENDER_TYPE(HttpStatus.BAD_REQUEST, "U400", "지원하지 않는 성별 타입입니다."),
    UNSUPPORTED_JOB_TYPE(HttpStatus.BAD_REQUEST, "U400", "지원하지 않는 직업 타입입니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "U400", "유효하지 않은 값을 입력하였습니다."),
    INVALID_INPUT_FORMAT(HttpStatus.BAD_REQUEST, "U400", "올바르지 않은 입력 형식입니다."),

    // JWT
    NOT_FOUND_REFRESH_TOKEN(HttpStatus.NOT_FOUND, "J404", "토큰이 존재하지 않습니다.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
