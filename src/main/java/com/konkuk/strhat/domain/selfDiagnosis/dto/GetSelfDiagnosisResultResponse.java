package com.konkuk.strhat.domain.selfDiagnosis.dto;

import com.konkuk.strhat.domain.selfDiagnosis.entity.SelfDiagnosis;
import com.konkuk.strhat.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetSelfDiagnosisResultResponse {
    private String nickname;
    private Integer score;
    private String type;
    private String selfDiagnosisLevel;

    public static GetSelfDiagnosisResultResponse of(User user,
                                                    SelfDiagnosis selfDiagnosis,
                                                    String selfDiagnosisLevel) {
        return GetSelfDiagnosisResultResponse.builder()
                .nickname(user.getNickname())
                .score(selfDiagnosis.getScore())
                .type(selfDiagnosis.getType().toString())
                .selfDiagnosisLevel(selfDiagnosisLevel)
                .build();
    }
}
