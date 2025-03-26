package com.konkuk.strhat.domain.user.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetUserInfoResponse {
    private String nickname;
    private Integer birth;
    private String gender;
    private String job;
    private String hobbyHealingStyle;
    private String stressReliefStyle;
    private String personality;

    @Builder
    public GetUserInfoResponse(String nickname,
                               Integer birth,
                               String gender,
                               String job,
                               String hobbyHealingStyle,
                               String stressReliefStyle,
                               String personality) {
        this.nickname = nickname;
        this.birth = birth;
        this.gender = gender;
        this.job = job;
        this.hobbyHealingStyle = hobbyHealingStyle;
        this.stressReliefStyle = stressReliefStyle;
        this.personality = personality;
    }
}
