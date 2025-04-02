package com.konkuk.strhat.domain.user.exception;

import com.konkuk.strhat.global.error.CustomException;
import com.konkuk.strhat.global.error.ErrorCode;

public class UnsupportedJobTypeException extends CustomException {

    public UnsupportedJobTypeException() {
        super(ErrorCode.UNSUPPORTED_JOB_TYPE);
    }
}
