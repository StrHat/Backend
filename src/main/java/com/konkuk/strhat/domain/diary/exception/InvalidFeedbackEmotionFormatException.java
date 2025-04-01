package com.konkuk.strhat.domain.diary.exception;

import com.konkuk.strhat.global.error.CustomException;
import com.konkuk.strhat.global.error.ErrorCode;

public class InvalidFeedbackEmotionFormatException extends CustomException {

    public InvalidFeedbackEmotionFormatException(String originalPositive, String OriginalNegative) {
        super(ErrorCode.INVALID_FEEDBACK_EMOTION_FORMAT,
                " [결과] (긍정): " + originalPositive + " (부정): " + OriginalNegative);
    }
}
