package com.konkuk.strhat.domain.stressScore.exception;

import com.konkuk.strhat.global.error.CustomException;
import com.konkuk.strhat.global.error.ErrorCode;

public class UnknownStressScoreGenerateException extends CustomException {
    public UnknownStressScoreGenerateException(String message) {
        super(ErrorCode.UNKNOWN_GPT_ERROR, message);
    }
}