package com.konkuk.strhat.domain.stressScore.exception;

import com.konkuk.strhat.global.error.CustomException;
import com.konkuk.strhat.global.error.ErrorCode;

public class ScoreOutOfRangeException extends CustomException {

    public ScoreOutOfRangeException(String message) {
        super(ErrorCode.SCORE_OUT_OF_RANGE, message);
    }
}
