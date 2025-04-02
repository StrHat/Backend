package com.konkuk.strhat.domain.user.exception;

import com.konkuk.strhat.global.error.CustomException;
import com.konkuk.strhat.global.error.ErrorCode;

public class UnsupportedGenderTypeException extends CustomException {

    public UnsupportedGenderTypeException() {
        super(ErrorCode.UNSUPPORTED_GENDER_TYPE);
    }
}
