package com.konkuk.strhat.domain.selfDiagnosis.exception;

import com.konkuk.strhat.global.error.CustomException;
import com.konkuk.strhat.global.error.ErrorCode;

public class NotFoundSelfDiagnosisException extends CustomException {

    public NotFoundSelfDiagnosisException() {
        super(ErrorCode.NOT_FOUND_RESOURCE, "자가진단 데이터가 존재하지 않습니다.");
    }
}
