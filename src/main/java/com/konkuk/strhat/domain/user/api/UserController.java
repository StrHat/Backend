package com.konkuk.strhat.domain.user.api;

import com.konkuk.strhat.domain.user.application.UserService;
import com.konkuk.strhat.domain.user.dto.PostSignUpRequest;
import com.konkuk.strhat.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("")
    public ApiResponse<Void> signIn(PostSignUpRequest request) {
        userService.createUser(request);
        return ApiResponse.successOnly();
    }
}
