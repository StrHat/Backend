package com.konkuk.strhat.domain.diary.exception;

import com.konkuk.strhat.global.error.CustomException;
import com.konkuk.strhat.global.error.ErrorCode;

public class InvalidFeedbackResultException extends CustomException {

    public InvalidFeedbackResultException() {
        super(ErrorCode.INVALID_FEEDBACK_RESULT);
    }
}
