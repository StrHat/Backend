package com.konkuk.strhat.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostKakaoSignInRequest {
    private String kakaoAccessToken;
}
