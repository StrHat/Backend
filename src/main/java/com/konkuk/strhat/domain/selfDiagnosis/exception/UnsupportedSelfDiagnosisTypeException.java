package com.konkuk.strhat.domain.selfDiagnosis.exception;

import com.konkuk.strhat.global.error.CustomException;
import com.konkuk.strhat.global.error.ErrorCode;

public class UnsupportedSelfDiagnosisTypeException extends CustomException {

    public UnsupportedSelfDiagnosisTypeException() {
        super(ErrorCode.UNSUPPORTED_SELF_DIAGNOSIS_TYPE);
    }
}
