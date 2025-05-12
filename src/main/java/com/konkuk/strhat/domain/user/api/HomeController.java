package com.konkuk.strhat.domain.user.api;

import com.konkuk.strhat.domain.user.application.HomeService;
import com.konkuk.strhat.domain.user.dto.GetHomeResponse;
import com.konkuk.strhat.global.response.ApiResponse;
import com.konkuk.strhat.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/home")
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;

    @PostMapping("")
    public ApiResponse<GetHomeResponse> getHomeInfo() {
        GetHomeResponse response = homeService.findHomeInfo(SecurityUtil.getCurrentUserId());
        return ApiResponse.success(response);
    }
}
