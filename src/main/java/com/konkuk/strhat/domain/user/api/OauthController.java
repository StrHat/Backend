package com.konkuk.strhat.domain.user.api;

import com.konkuk.strhat.domain.user.application.KakaoOAuthService;
import com.konkuk.strhat.domain.user.dto.PostKakaoSignInRequest;
import com.konkuk.strhat.domain.user.dto.PostKakaoSignInResponse;
import com.konkuk.strhat.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class OauthController {

    private final KakaoOAuthService kakaoOAuthService;

    @PostMapping("/kakao")
    public ApiResponse<PostKakaoSignInResponse> kakaoSignIn(@RequestBody PostKakaoSignInRequest request) {
        PostKakaoSignInResponse response = kakaoOAuthService.getUserProfileByToken(request);
        return ApiResponse.success(response);
    }
}
