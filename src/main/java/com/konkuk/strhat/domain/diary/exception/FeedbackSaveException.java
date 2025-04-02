package com.konkuk.strhat.domain.diary.exception;

import com.konkuk.strhat.global.error.CustomException;
import com.konkuk.strhat.global.error.ErrorCode;

public class FeedbackSaveException extends CustomException {

    public FeedbackSaveException() {
        super(ErrorCode.DATABASE_SAVE_ERROR, "피드백 데이터를 저장하는 도중 오류가 발생했습니다.");
    }
}