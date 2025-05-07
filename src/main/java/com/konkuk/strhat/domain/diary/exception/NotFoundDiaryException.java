package com.konkuk.strhat.domain.diary.exception;

import com.konkuk.strhat.global.error.CustomException;
import com.konkuk.strhat.global.error.ErrorCode;

public class NotFoundDiaryException extends CustomException {

    public NotFoundDiaryException() {
        super(ErrorCode.NOT_FOUND_RESOURCE, "일기 데이터가 존재하지 않습니다.");
    }
}
