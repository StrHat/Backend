package com.konkuk.strhat.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostKakaoSignInRequest {
    @NotBlank
    private String kakaoAccessToken;
}
