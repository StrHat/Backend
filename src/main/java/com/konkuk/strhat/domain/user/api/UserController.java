package com.konkuk.strhat.domain.user.api;

import com.konkuk.strhat.domain.user.application.UserService;
import com.konkuk.strhat.domain.user.dto.GetUserInfoResponse;
import com.konkuk.strhat.domain.user.dto.PostSignUpRequest;
import com.konkuk.strhat.domain.user.dto.TokenDto;
import com.konkuk.strhat.domain.user.entity.CustomUserDetails;
import com.konkuk.strhat.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ApiResponse<TokenDto> signIn(@RequestBody PostSignUpRequest request) {
        TokenDto response = userService.createUser(request);
        return ApiResponse.success(response);
    }

    @PostMapping("/sign-out")
    public ApiResponse<Void> signOut(@AuthenticationPrincipal CustomUserDetails userDetails) {
        userService.processSignOut(userDetails.getEmail());
        return ApiResponse.successOnly();
    }

    @GetMapping("")
    public ApiResponse<GetUserInfoResponse> readUserInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        GetUserInfoResponse response = userService.findUserInfo(customUserDetails.getId());
        return ApiResponse.success(response);
    }
}
