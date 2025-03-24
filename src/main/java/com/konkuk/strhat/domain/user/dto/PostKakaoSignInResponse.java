package com.konkuk.strhat.domain.user.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostKakaoSignInResponse {

    private boolean userExists;
    private String email;

    @Builder
    public PostKakaoSignInResponse(boolean userExists, String email) {
        this.userExists = userExists;
        this.email = email;
    }
}
