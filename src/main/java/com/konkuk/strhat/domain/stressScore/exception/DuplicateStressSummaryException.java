package com.konkuk.strhat.domain.stressScore.exception;

import com.konkuk.strhat.global.error.CustomException;
import com.konkuk.strhat.global.error.ErrorCode;

public class DuplicateStressSummaryException extends CustomException {
    public DuplicateStressSummaryException() {
        super(ErrorCode.DUPLICATE_RESOURCE, "해당 날짜에 이미 주간 스트레스 요약 데이터가 존재합니다.");
    }
}
