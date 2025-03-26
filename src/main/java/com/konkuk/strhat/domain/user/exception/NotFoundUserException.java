package com.konkuk.strhat.domain.user.exception;

import com.konkuk.strhat.global.exception.CustomException;
import com.konkuk.strhat.global.exception.ErrorCode;

public class NotFoundUserException extends CustomException {

    public NotFoundUserException() {
        super(ErrorCode.NOT_FOUND_USER);
    }
}
