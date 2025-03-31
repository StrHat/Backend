package com.konkuk.strhat.global.error;

import com.konkuk.strhat.global.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        ErrorResponse errorResponse = ErrorResponse.withMessage(e.getErrorCode(), e.getMessage());
        return handleException(e, errorResponse);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e) {
        ErrorResponse errorResponse = ErrorResponse.withAppendedMessage(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage());
        return handleException(e, errorResponse);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(Exception e) {
        ErrorResponse errorResponse = ErrorResponse.withAppendedMessage(ErrorCode.METHOD_NOT_ALLOWED, e.getMessage());
        return handleException(e, errorResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatchException(MethodArgumentTypeMismatchException e) {
        ErrorResponse errorResponse = ErrorResponse.withAppendedMessage(ErrorCode.INVALID_INPUT_VALUE, e.getMessage());
        return handleException(e, errorResponse);
    }

    private ResponseEntity<ErrorResponse> handleException(Exception e, ErrorResponse errorResponse) {
        log.error("[{}] {}: {}", errorResponse.getCode(), e.getClass().getSimpleName(), e.getMessage());
        return ResponseEntity
                .status(errorResponse.getStatus())
                .body(errorResponse);
    }

}
