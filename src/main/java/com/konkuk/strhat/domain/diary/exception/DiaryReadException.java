package com.konkuk.strhat.domain.diary.exception;

import com.konkuk.strhat.global.error.CustomException;
import com.konkuk.strhat.global.error.ErrorCode;

public class DiaryReadException extends CustomException {

    public DiaryReadException() {
        super(ErrorCode.DATABASE_READ_ERROR, "일기 데이터를 불러오는 도중 오류가 발생했습니다.");
    }
}
