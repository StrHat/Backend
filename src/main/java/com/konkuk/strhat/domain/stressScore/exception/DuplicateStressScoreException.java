package com.konkuk.strhat.domain.stressScore.exception;

import com.konkuk.strhat.global.error.CustomException;
import com.konkuk.strhat.global.error.ErrorCode;

public class DuplicateStressScoreException extends CustomException {
    public DuplicateStressScoreException() {
        super(ErrorCode.DUPLICATE_RESOURCE, "해당 날짜에 이미 스트레스 점수 데이터가 존재합니다.");
    }
}
