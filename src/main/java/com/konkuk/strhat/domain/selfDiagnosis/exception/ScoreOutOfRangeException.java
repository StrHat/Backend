package com.konkuk.strhat.domain.selfDiagnosis.exception;

import com.konkuk.strhat.global.error.CustomException;
import com.konkuk.strhat.global.error.ErrorCode;

public class ScoreOutOfRangeException extends CustomException {

    public ScoreOutOfRangeException() {
        super(ErrorCode.SCORE_OUT_OF_RANGE);
    }
}
