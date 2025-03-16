package com.konkuk.strhat.global.response;

import com.konkuk.strhat.global.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorResponse {
    private final int status;
    private final String code;
    private final String message;

    public static ErrorResponse from(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .status(errorCode.getHttpStatus().value())
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
    }

    public static ErrorResponse of(ErrorCode errorCode, String message) {
        return ErrorResponse.builder()
                .status(errorCode.getHttpStatus().value())
                .code(errorCode.getCode())
                .message(errorCode.getMessage() + " - " + message)
                .build();
    }
}
