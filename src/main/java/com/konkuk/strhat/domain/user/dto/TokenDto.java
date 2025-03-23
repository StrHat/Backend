package com.konkuk.strhat.domain.user.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenDto {

    private String accessToken;
    private String refreshToken;
    private Instant refreshTokenExpiredAt;

    @Builder
    public TokenDto(String accessToken, String refreshToken, Instant refreshTokenExpiredAt) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.refreshTokenExpiredAt = refreshTokenExpiredAt;
    }

    public static TokenDto empty() {
        return TokenDto.builder()
                .accessToken(null)
                .refreshToken(null)
                .refreshTokenExpiredAt(null)
                .build();
    }
}
