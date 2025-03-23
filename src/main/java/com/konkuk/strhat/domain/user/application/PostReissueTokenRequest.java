package com.konkuk.strhat.domain.user.application;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostReissueTokenRequest {
    private String refreshToken;
}
