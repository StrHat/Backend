package com.konkuk.strhat.domain.user.dto;

import lombok.*;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = PRIVATE)
public class PostKakaoSignInResponse {

    private final boolean userExists;
    private final Long kakaoId;

    public static PostKakaoSignInResponse of(boolean userExists, Long kakaoId) {
        return PostKakaoSignInResponse.builder()
                .userExists(userExists)
                .kakaoId(kakaoId)
                .build();
    }
}
