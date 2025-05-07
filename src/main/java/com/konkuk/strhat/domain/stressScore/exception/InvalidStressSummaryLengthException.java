package com.konkuk.strhat.domain.stressScore.exception;

import com.konkuk.strhat.global.error.CustomException;
import com.konkuk.strhat.global.error.ErrorCode;

public class InvalidStressSummaryLengthException extends CustomException {

    public InvalidStressSummaryLengthException(int limitedLength, int generatedLength) {
        super(ErrorCode.INVALID_SUMMARY_SIZE, "제한 길이: " + limitedLength + "생성된 길이: " + generatedLength);
    }
}
