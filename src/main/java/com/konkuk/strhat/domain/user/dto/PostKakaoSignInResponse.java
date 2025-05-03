package com.konkuk.strhat.domain.user.dto;

import lombok.*;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = PRIVATE)
public class PostKakaoSignInResponse {

    private final boolean userExists;
    private final String email;

    public static PostKakaoSignInResponse of(boolean userExists, String email) {
        return PostKakaoSignInResponse.builder()
                .userExists(userExists)
                .email(email)
                .build();
    }
}
