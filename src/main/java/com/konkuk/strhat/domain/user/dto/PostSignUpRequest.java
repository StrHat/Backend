package com.konkuk.strhat.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostSignUpRequest {
    private String email;
    private String nickname;
    private Integer birth;
    private String gender;
    private String job;
    private String hobbyHealingStyle;
    private String stressReliefStyle;
    private String personality;
}
