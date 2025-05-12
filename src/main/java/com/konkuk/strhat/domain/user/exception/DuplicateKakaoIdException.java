package com.konkuk.strhat.domain.user.exception;

import com.konkuk.strhat.global.error.CustomException;
import com.konkuk.strhat.global.error.ErrorCode;

public class DuplicateKakaoIdException extends CustomException {

    public DuplicateKakaoIdException() {
        super(ErrorCode.DUPLICATE_KAKAO_ID);
    }
}
