package com.konkuk.strhat.global.exception;

import com.konkuk.strhat.global.response.ApiResponse;
import com.konkuk.strhat.global.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ApiResponse<?> handleRuntimeException(RuntimeException e) {
        return handleException(e, ErrorResponse.from(ErrorCode.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(CustomException.class)
    public ApiResponse<?> handleCustomException(CustomException e) {
        return handleException(e, ErrorResponse.from(e.getErrorCode()));
    }

    public ApiResponse<?> handleException(Exception e, ErrorResponse errorResponse) {
        log.error("{}: {}", errorResponse.getCode(), e.getMessage());
        return ApiResponse.error(errorResponse);
    }
}
