package com.konkuk.strhat.domain.user.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostKakaoSignInResponse {

    private boolean isExist;
    private TokenDto tokenDto;

    @Builder
    public PostKakaoSignInResponse(boolean isExist, TokenDto tokenDto) {
        this.isExist = isExist;
        this.tokenDto = tokenDto;
    }
}
