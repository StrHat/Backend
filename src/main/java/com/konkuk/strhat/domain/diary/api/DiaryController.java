package com.konkuk.strhat.domain.diary.api;

import com.konkuk.strhat.domain.diary.application.AIFeedbackService;
import com.konkuk.strhat.domain.diary.application.DiaryService;
import com.konkuk.strhat.domain.diary.dto.*;
import com.konkuk.strhat.domain.diary.entity.Diary;
import com.konkuk.strhat.domain.diary.entity.Feedback;
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
    private final AIFeedbackService aiFeedbackService;

    @Operation(summary = "작성된 일기 존재 여부 확인", description = "작성된 일기가 있는지 확인한다.")
    @GetMapping("/exists")
    public ApiResponse<CheckDiaryResponse> checkDiaryExist(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        Long userId = SecurityUtil.getCurrentUserId();
        CheckDiaryResponse response = diaryService.checkDiaryExists(userId, date);
        return ApiResponse.success(response);
    }

    @Operation(summary = "일기 작성 및 피드백 조회", description = "사용자가 작성한 일기를 저장하고, AI 피드백을 받는다.")
    @PostMapping
    public ApiResponse<DiarySaveResponse> saveDiary(@Valid @RequestBody DiarySaveRequest request) {
        Long userId = SecurityUtil.getCurrentUserId();
        Diary diary = diaryService.saveDiary(userId, request);
        Feedback feedback = aiFeedbackService.generateFeedback(diary);
        DiarySaveResponse response = diaryService.saveFeedback(feedback);
        return ApiResponse.success(response);
    }

    @Operation(summary = "일기 조회", description = "사용자가 작성한 일기를 조회한다. (일기 전문보기)")
    @GetMapping
    public ApiResponse<DiaryContentResponse> readDiary(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        Long userId = SecurityUtil.getCurrentUserId();
        DiaryContentResponse response = diaryService.readDiary(userId, date);
        return ApiResponse.success(response);
    }

    @Operation(summary = "피드백 조회", description = "사용자가 작성한 일기에 대한 피드백을 조회한다.")
    @GetMapping("/feedback")
    public ApiResponse<FeedbackResponse> readFeedback(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        Long userId = SecurityUtil.getCurrentUserId();
        FeedbackResponse response = diaryService.readFeedback(userId, date);
        return ApiResponse.success(response);
    }

}
