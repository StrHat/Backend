package com.konkuk.strhat.domain.selfDiagnosis.api;

import com.konkuk.strhat.domain.selfDiagnosis.application.SelfDiagnosisService;
import com.konkuk.strhat.domain.selfDiagnosis.dto.SelfDiagnosisQuestion;
import com.konkuk.strhat.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
