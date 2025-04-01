package com.konkuk.strhat.domain.diary.api;

import com.konkuk.strhat.domain.diary.application.DiaryService;
import com.konkuk.strhat.domain.diary.dto.CheckDiaryResponse;
import com.konkuk.strhat.domain.diary.dto.DiarySaveRequest;
import com.konkuk.strhat.domain.diary.dto.DiarySaveResponse;
import com.konkuk.strhat.domain.diary.entity.Diary;
import com.konkuk.strhat.global.response.ApiResponse;
import com.konkuk.strhat.global.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@RestController
@RequestMapping("/api/v1/diary")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    @Operation(summary = "작성된 일기 존재 여부 확인", description = "작성된 일기가 있는지 확인한다.")
    @GetMapping("/exists")
    public ApiResponse<CheckDiaryResponse> checkDiaryExist(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        Long userId = SecurityUtil.getCurrentUserId();
        CheckDiaryResponse response = diaryService.checkDiaryExist(userId, date);
        return ApiResponse.success(response);
    }

    @Operation(summary = "일기 작성 및 피드백 조회", description = "사용자가 작성한 일기를 저장하고, AI 피드백을 받는다.")
    @PostMapping
    public ApiResponse<DiarySaveResponse> saveDiary(@Valid @RequestBody DiarySaveRequest request) {
        Long userId = SecurityUtil.getCurrentUserId();
        Diary diary = diaryService.saveDiary(userId, request);
        DiarySaveResponse response = diaryService.getFeedback(diary);
        return ApiResponse.success(response);
    }

}
