package com.konkuk.strhat.domain.stressScore.exception;

import com.konkuk.strhat.global.error.CustomException;
import com.konkuk.strhat.global.error.ErrorCode;

public class InvalidStressSummaryResultException extends CustomException {

    public InvalidStressSummaryResultException() {
        super(ErrorCode.INVALID_SUMMARY_RESULT);
    }
}
