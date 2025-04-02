package com.konkuk.strhat.domain.diary.exception;

import com.konkuk.strhat.global.error.CustomException;
import com.konkuk.strhat.global.error.ErrorCode;

public class DuplicateDiaryException extends CustomException {
    public DuplicateDiaryException() {
        super(ErrorCode.DUPLICATE_RESOURCE, "해당 날짜에 이미 일기 데이터가 존재합니다.");
    }
}
