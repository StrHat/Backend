package com.konkuk.strhat.domain.user.api;

import com.konkuk.strhat.domain.user.application.KakaoOAuthService;
import com.konkuk.strhat.domain.user.dto.PostKakaoSignInRequest;
import com.konkuk.strhat.domain.user.dto.PostKakaoSignInResponse;
import com.konkuk.strhat.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
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

    @Operation(summary = "카카오 검증 및 로그인", description = "카카오 계정 유무를 검증하고 로그인한다.")
    @PostMapping("/kakao")
    public ApiResponse<PostKakaoSignInResponse> kakaoSignIn(@Valid @RequestBody PostKakaoSignInRequest request, HttpServletResponse httpServletResponse) {
        PostKakaoSignInResponse response = kakaoOAuthService.getUserIdByToken(request, httpServletResponse);
        return ApiResponse.success(response);
    }
}
