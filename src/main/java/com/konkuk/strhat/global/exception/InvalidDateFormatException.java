package com.konkuk.strhat.global.exception;

import com.konkuk.strhat.global.error.CustomException;
import com.konkuk.strhat.global.error.ErrorCode;

public class InvalidDateFormatException extends CustomException {
    public InvalidDateFormatException(String inputDate) {
        super(ErrorCode.INVALID_INPUT_VALUE, "입력된 날짜 형식: " + inputDate);
    }
}
