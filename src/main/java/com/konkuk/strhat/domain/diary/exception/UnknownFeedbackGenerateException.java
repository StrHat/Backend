package com.konkuk.strhat.domain.diary.exception;

import com.konkuk.strhat.global.error.CustomException;
import com.konkuk.strhat.global.error.ErrorCode;

public class UnknownFeedbackGenerateException extends CustomException {
    public UnknownFeedbackGenerateException(String message) {
        super(ErrorCode.UNKNOWN_GPT_ERROR, message);
    }
}