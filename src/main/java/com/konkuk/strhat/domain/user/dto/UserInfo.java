package com.konkuk.strhat.domain.user.dto;

import com.konkuk.strhat.domain.user.entity.User;
import com.konkuk.strhat.domain.user.enums.Gender;
import com.konkuk.strhat.domain.user.enums.Job;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfo {

    private final String nickname;
    private final Integer birth;
    private final Gender gender;
    private final Job job;
    private final String hobbyHealingStyle;
    private final String stressReliefStyle;
    private final String personality;

    public static UserInfo from(User user) {
        return UserInfo.builder()
                .nickname(user.getNickname())
                .birth(user.getBirth())
                .gender(user.getGender())
                .job(user.getJob())
                .hobbyHealingStyle(user.getHobbyHealingStyle())
                .stressReliefStyle(user.getStressReliefStyle())
                .personality(user.getPersonality())
                .build();
    }
}
