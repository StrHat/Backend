package com.konkuk.strhat.domain.diary.exception;

import com.konkuk.strhat.global.error.CustomException;
import com.konkuk.strhat.global.error.ErrorCode;

public class FeedbackGenerateException extends CustomException {

    public FeedbackGenerateException(String message) {
        super(ErrorCode.GPT_FEEDBACK_GENERATION_FAIL, message);
    }
}
