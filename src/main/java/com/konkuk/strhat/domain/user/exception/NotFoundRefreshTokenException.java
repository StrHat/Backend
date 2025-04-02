package com.konkuk.strhat.domain.user.exception;

import com.konkuk.strhat.global.error.CustomException;
import com.konkuk.strhat.global.error.ErrorCode;

public class NotFoundRefreshTokenException extends CustomException {

    public NotFoundRefreshTokenException() {
        super(ErrorCode.NOT_FOUND_REFRESH_TOKEN);
    }
}
