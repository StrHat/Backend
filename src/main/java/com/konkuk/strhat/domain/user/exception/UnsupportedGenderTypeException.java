package com.konkuk.strhat.domain.user.exception;

import com.konkuk.strhat.global.exception.CustomException;
import com.konkuk.strhat.global.exception.ErrorCode;

public class UnsupportedGenderTypeException extends CustomException {

    public UnsupportedGenderTypeException() {
        super(ErrorCode.UNSUPPORTED_GENDER_TYPE);
    }
}
