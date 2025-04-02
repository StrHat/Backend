package com.konkuk.strhat.domain.user.exception;

import com.konkuk.strhat.global.error.CustomException;
import com.konkuk.strhat.global.error.ErrorCode;

public class MissingTokenException extends CustomException {

    public MissingTokenException() {
        super(ErrorCode.MISSING_TOKEN);
    }
}
