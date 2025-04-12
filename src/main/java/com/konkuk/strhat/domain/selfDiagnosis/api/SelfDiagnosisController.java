package com.konkuk.strhat.domain.selfDiagnosis.api;

import com.konkuk.strhat.domain.selfDiagnosis.application.SelfDiagnosisService;
import com.konkuk.strhat.domain.selfDiagnosis.dto.GetSelfDiagnosisResultResponse;
import com.konkuk.strhat.domain.selfDiagnosis.dto.PostSelfDiagnosisRequest;
import com.konkuk.strhat.domain.selfDiagnosis.dto.SelfDiagnosisQuestion;
import com.konkuk.strhat.global.response.ApiResponse;
import com.konkuk.strhat.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/self-diagnoses")
public class SelfDiagnosisController {

    private final SelfDiagnosisService selfDiagnosisService;

    @GetMapping("")
    public ApiResponse<List<SelfDiagnosisQuestion>> readSelfDiagnosis(@RequestParam String type) {
        List<SelfDiagnosisQuestion> response = selfDiagnosisService.findSelfDiagnosis(type);
        return ApiResponse.success(response);
    }

    @PostMapping("")
    public ApiResponse<Void> createSelfDiagnosisResult(@RequestBody PostSelfDiagnosisRequest request) {
        selfDiagnosisService.generateSelfDiagnosisResult(request, SecurityUtil.getCurrentUserId());
        return ApiResponse.successOnly();
    }

    @GetMapping("/result")
    public ApiResponse<GetSelfDiagnosisResultResponse> readSelfDiagnosisResult(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        GetSelfDiagnosisResultResponse response = selfDiagnosisService.findSelfDiagnosisResult(date, SecurityUtil.getCurrentUserId());
        return ApiResponse.success(response);
    }
}
