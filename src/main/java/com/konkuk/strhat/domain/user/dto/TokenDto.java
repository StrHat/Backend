package com.konkuk.strhat.domain.user.dto;

import lombok.*;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = PRIVATE)
public class TokenDto {

    private String accessToken;
    private String refreshToken;

    public static TokenDto of(String accessToken, String refreshToken) {
        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public static TokenDto empty() {
        return TokenDto.builder()
                .accessToken(null)
                .refreshToken(null)
                .build();
    }
}
