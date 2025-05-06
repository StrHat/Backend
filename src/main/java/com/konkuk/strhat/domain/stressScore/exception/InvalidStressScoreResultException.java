package com.konkuk.strhat.domain.stressScore.exception;

import com.konkuk.strhat.global.error.CustomException;
import com.konkuk.strhat.global.error.ErrorCode;

public class InvalidStressScoreResultException extends CustomException {

    public InvalidStressScoreResultException() {
        super(ErrorCode.INVALID_STRESS_SCORE_RESULT);
    }
}
