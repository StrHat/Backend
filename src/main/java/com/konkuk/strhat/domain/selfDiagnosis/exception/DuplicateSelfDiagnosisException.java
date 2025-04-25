package com.konkuk.strhat.domain.selfDiagnosis.exception;

import com.konkuk.strhat.global.error.CustomException;
import com.konkuk.strhat.global.error.ErrorCode;

public class DuplicateSelfDiagnosisException extends CustomException {

    public DuplicateSelfDiagnosisException() {
        super(ErrorCode.DUPLICATE_RESOURCE, "해당 날짜에 이미 자가진단 검사 데이터가 존재합니다.");
    }
}
