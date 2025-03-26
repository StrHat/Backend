package com.konkuk.strhat.domain.user.exception;

import com.konkuk.strhat.global.exception.CustomException;
import com.konkuk.strhat.global.exception.ErrorCode;

public class DuplicateEmailException extends CustomException {

    public DuplicateEmailException() {
        super(ErrorCode.DUPLICATE_EMAIL);
    }
}
