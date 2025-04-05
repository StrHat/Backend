package com.konkuk.strhat.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // GLOBAL
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "G500", "서버 내부에 문제가 발생했습니다."),
    DATABASE_SAVE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "G500", "데이터베이스 저장에 실패하였습니다."),
    DATABASE_READ_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "G500", "데이터베이스 조회에 실패하였습니다."),

    // USER
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "U404", "유저가 존재하지 않습니다"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "U405", "허용되지 않는 메서드입니다"),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "U409", "이미 존재하는 이메일입니다."),
    DUPLICATE_RESOURCE(HttpStatus.CONFLICT, "U409", "중복해서 저장할 수 없습니다."),
    UNSUPPORTED_GENDER_TYPE(HttpStatus.BAD_REQUEST, "U400", "지원하지 않는 성별 타입입니다."),
    UNSUPPORTED_JOB_TYPE(HttpStatus.BAD_REQUEST, "U400", "지원하지 않는 직업 타입입니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "U400", "올바르지 않은 값 또는 형식입니다."),

    // JWT
    NOT_FOUND_REFRESH_TOKEN(HttpStatus.NOT_FOUND, "J404", "토큰이 존재하지 않습니다."),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "J400", "올바르지 않은 토큰값입니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "J401", "토큰의 유효기간이 만료되었습니다."),
    MISSING_TOKEN(HttpStatus.UNAUTHORIZED, "J401", "헤더에 토큰이 존재하지 않습니다."),

    // AI (GPI API)
    GPT_FEEDBACK_GENERATION_FAIL(HttpStatus.BAD_GATEWAY, "A502", "GPT API를 이용한 피드백 생성에 실패하였습니다."),
    INVALID_FEEDBACK_EMOTION_FORMAT(HttpStatus.BAD_GATEWAY, "A502", "GPT API를 통해 생성된 피드백 결과 중 감정 키워드 형식이 잘못되었습니다.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
