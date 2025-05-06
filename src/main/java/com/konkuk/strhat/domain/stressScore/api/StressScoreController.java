package com.konkuk.strhat.domain.stressScore.api;

import com.konkuk.strhat.domain.stressScore.application.AIWeeklyStressSummaryService;
import com.konkuk.strhat.domain.stressScore.application.StressScoreService;
import com.konkuk.strhat.domain.stressScore.dto.DailyStressScoreResponse;
import com.konkuk.strhat.domain.stressScore.dto.WeeklyStressSummaryResponse;
import com.konkuk.strhat.domain.stressScore.entity.StressScore;
import com.konkuk.strhat.domain.stressScore.entity.StressSummary;
import com.konkuk.strhat.domain.user.application.UserService;
import com.konkuk.strhat.domain.user.entity.User;
import com.konkuk.strhat.global.response.ApiResponse;
import com.konkuk.strhat.global.util.DateUtils;
import com.konkuk.strhat.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.konkuk.strhat.domain.stressScore.application.AIDailyStressScoreService;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/stress-score")
@RequiredArgsConstructor
public class StressScoreController {
    private final StressScoreService stressScoreService;
    private final AIDailyStressScoreService aiDailyStressScoreService;
    private final AIWeeklyStressSummaryService aiWeeklyStressSummaryService;
    private final UserService userService;

    @GetMapping("/daily")
    public ApiResponse<DailyStressScoreResponse> getDailyStressScore(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        User user = userService.findById(SecurityUtil.getCurrentUserId());

        Optional<DailyStressScoreResponse> dailyStressScoreResponse = stressScoreService.readDailyStressScore(date, user);

        if(dailyStressScoreResponse.isPresent()){
            return ApiResponse.success(dailyStressScoreResponse.get());
        }else{
            StressScore stressScore = aiDailyStressScoreService.generateDailyStressScore(date, user);
            DailyStressScoreResponse response = stressScoreService.saveDailyStressScore(stressScore, user);
            return ApiResponse.success(response);
        }
    }

    @GetMapping("/weekly")
    public ApiResponse<WeeklyStressSummaryResponse> getWeeklyStressSummary(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        User user = userService.findById(SecurityUtil.getCurrentUserId());
        LocalDate weekStartDate = DateUtils.getStartOfWeek(date);
        LocalDate weekEndDate = DateUtils.getEndOfWeek(date);

        Optional<WeeklyStressSummaryResponse> weeklyStressSummaryResponse = stressScoreService.readStressSummary(weekStartDate, weekEndDate, user);

        if (weeklyStressSummaryResponse.isPresent()){
            return ApiResponse.success(weeklyStressSummaryResponse.get());
        }else{
            StressSummary summary = aiWeeklyStressSummaryService.generateWeeklyStressSummary(user, weekStartDate, weekEndDate);
            WeeklyStressSummaryResponse response = stressScoreService.saveStressSummary(summary, user);
            return ApiResponse.success(response);
        }
    }
}
