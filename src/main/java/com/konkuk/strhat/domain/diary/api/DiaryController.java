package com.konkuk.strhat.domain.diary.api;

import com.konkuk.strhat.domain.diary.application.DiaryService;
import com.konkuk.strhat.domain.diary.dto.CheckDiaryResponse;
import com.konkuk.strhat.global.response.ApiResponse;
import com.konkuk.strhat.global.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/diary")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    @Operation(summary = "작성된 일기 존재 여부 확인", description = "작성된 일기가 있는지 확인한다.")
    @GetMapping("/exists")
    public ApiResponse<CheckDiaryResponse> signIn(@RequestParam String date) {
        Long userId = SecurityUtil.getCurrentUserId();
        CheckDiaryResponse response = diaryService.checkDiaryExist(userId, date);
        return ApiResponse.success(response);
    }

}
