package com.konkuk.strhat.domain.user.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = PRIVATE)
public class GetUserInfoResponse {
    private final String nickname;
    private final Integer birth;
    private final String gender;
    private final String job;
    private final String hobbyHealingStyle;
    private final String stressReliefStyle;
    private final String personality;

    public static GetUserInfoResponse of(String nickname, Integer birth, String gender, String job,
                                         String hobbyHealingStyle, String stressReliefStyle, String personality) {
        return GetUserInfoResponse.builder()
                .nickname(nickname)
                .birth(birth)
                .gender(gender)
                .job(job)
                .hobbyHealingStyle(hobbyHealingStyle)
                .stressReliefStyle(stressReliefStyle)
                .personality(personality)
                .build();
    }
}
