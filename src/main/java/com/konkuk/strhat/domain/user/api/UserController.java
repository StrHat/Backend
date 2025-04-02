package com.konkuk.strhat.domain.user.api;

import com.konkuk.strhat.domain.user.dto.PostReissueTokenRequest;
import com.konkuk.strhat.domain.user.application.UserService;
import com.konkuk.strhat.domain.user.dto.GetUserInfoResponse;
import com.konkuk.strhat.domain.user.dto.PatchBasicInfoRequest;
import com.konkuk.strhat.domain.user.dto.PatchHobbyHealingStyleRequest;
import com.konkuk.strhat.domain.user.dto.PatchPersonalityRequest;
import com.konkuk.strhat.domain.user.dto.PatchStressReliefStyleRequest;
import com.konkuk.strhat.domain.user.dto.PostSignUpRequest;
import com.konkuk.strhat.domain.user.dto.TokenDto;
import com.konkuk.strhat.global.response.ApiResponse;
import com.konkuk.strhat.global.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원가입", description = "회원가입한다.")
    @PostMapping("/sign-up")
    public ApiResponse<Void> signIn(@Valid @RequestBody PostSignUpRequest request, HttpServletResponse httpServletResponse) {
        TokenDto response = userService.createUser(request, httpServletResponse);
        return ApiResponse.successOnly();
    }

    @Operation(summary = "로그아웃" ,description = "로그아웃한다.")
    @PostMapping("/sign-out")
    public ApiResponse<Void> signOut() {
        userService.processSignOut(SecurityUtil.getCurrentUserEmail());
        return ApiResponse.successOnly();
    }

    @Operation(summary = "유저 정보 조회", description = "유저의 정보를 조회한다.")
    @GetMapping("")
    public ApiResponse<GetUserInfoResponse> readUserInfo() {
        GetUserInfoResponse response = userService.findUserInfo(SecurityUtil.getCurrentUserId());
        return ApiResponse.success(response);
    }

    @Operation(summary = "기본 정보 변경", description = "유저의 닉네임, 태어난 년도, 성별, 직업을 변경한다.")
    @PatchMapping("/info")
    public ApiResponse<Void> updateUserBasicInfo(@Valid @RequestBody PatchBasicInfoRequest request) {
        userService.modifyUserBasicInfo(request, SecurityUtil.getCurrentUserId());
        return ApiResponse.successOnly();
    }

    @Operation(summary = "취미 및 힐링 방법 변경", description = "유저의 취미 및 힐링 방법을 변경한다.")
    @PatchMapping("/hobby-healing-style")
    public ApiResponse<Void> updateHobbyHealingStyle(@Valid @RequestBody PatchHobbyHealingStyleRequest request) {
        userService.modifyHobbyHealingStyle(request, SecurityUtil.getCurrentUserId());
        return ApiResponse.successOnly();
    }

    @Operation(summary = "스트레스 해소 방법 변경", description = "유저의 기존 스트레스 해소 방법을 변경한다.")
    @PatchMapping("/stress-relief-style")
    public ApiResponse<Void> updateStressReliefStyle(@Valid @RequestBody PatchStressReliefStyleRequest request) {
        userService.modifyStressReliefStyle(request, SecurityUtil.getCurrentUserId());
        return ApiResponse.successOnly();
    }

    @Operation(summary = "성향 변경", description = "유저의 기존 성향 정보를 변경한다.")
    @PatchMapping("/personality")
    public ApiResponse<Void> updatePersonality(@Valid @RequestBody PatchPersonalityRequest request) {
        userService.modifyPersonality(request, SecurityUtil.getCurrentUserId());
        return ApiResponse.successOnly();
    }

    @Operation(summary = "토큰 재발급", description = "AccessToken을 재발급한다.")
    @PostMapping("/reissue-token")
    public ApiResponse<Void> reissueToken(@Valid @RequestBody PostReissueTokenRequest request,
                                          HttpServletResponse httpServletResponse) {
        userService.processReissue(request, httpServletResponse);
        return ApiResponse.successOnly();
    }
}
