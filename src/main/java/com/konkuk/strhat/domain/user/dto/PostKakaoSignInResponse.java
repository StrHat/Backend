package com.konkuk.strhat.domain.user.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostKakaoSignInResponse {

    private boolean userExists;
    private TokenDto tokenDto;

    @Builder
    public PostKakaoSignInResponse(boolean userExists, TokenDto tokenDto) {
        this.userExists = userExists;
        this.tokenDto = tokenDto;
    }
}
