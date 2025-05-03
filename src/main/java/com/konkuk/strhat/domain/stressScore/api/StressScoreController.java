package com.konkuk.strhat.domain.stressScore.api;

import com.konkuk.strhat.domain.stressScore.dto.DailyStressScoreResponse;
import com.konkuk.strhat.domain.stressScore.dto.WeeklyStressSummaryResponse;
import com.konkuk.strhat.global.response.ApiResponse;
import com.konkuk.strhat.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.konkuk.strhat.domain.stressScore.application.StressScoreService;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/stress-score")
@RequiredArgsConstructor
public class StressScoreController {
    private final StressScoreService stressScoreService;

    @GetMapping("/daily")
    public ApiResponse<DailyStressScoreResponse> getDailyStressScore(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        Long userId = SecurityUtil.getCurrentUserId();
        DailyStressScoreResponse response = stressScoreService.getOrCreateStressScore(date, userId);
        return ApiResponse.success(response);
    }

    @GetMapping("/weekly")
    public ApiResponse<WeeklyStressSummaryResponse> getWeeklyStressSummary(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        Long userId = SecurityUtil.getCurrentUserId();
        WeeklyStressSummaryResponse response = stressScoreService.getOrCreateWeeklyStressSummary(date, userId);
        return ApiResponse.success(response);
    }
}
