package com.konkuk.strhat.global.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.konkuk.strhat.global.error.ErrorCode;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonPropertyOrder({"success", "status", "code", "message"})
public class ErrorResponse {
    @JsonProperty(value = "isSuccess")
    private final boolean success = false;
    private final int status;
    private final String code;
    private final String message;

    public static ErrorResponse fromErrorCode(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .status(errorCode.getHttpStatus().value())
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
    }

    public static ErrorResponse withMessage(ErrorCode errorCode, String message) {
        return ErrorResponse.builder()
                .status(errorCode.getHttpStatus().value())
                .code(errorCode.getCode())
                .message(message)
                .build();
    }

    public static ErrorResponse withAppendedMessage(ErrorCode errorCode, String message) {
        return ErrorResponse.builder()
                .status(errorCode.getHttpStatus().value())
                .code(errorCode.getCode())
                .message(errorCode.getMessage() + " - " + message)
                .build();
    }
}
