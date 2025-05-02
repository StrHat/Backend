package com.konkuk.strhat.domain.user.dto;

import com.konkuk.strhat.domain.user.entity.User;
import com.konkuk.strhat.domain.user.enums.Gender;
import com.konkuk.strhat.domain.user.enums.Job;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = PRIVATE)
public class UserInfoDto {

    private final String nickname;
    private final Integer birth;
    private final Gender gender;
    private final Job job;
    private final String hobbyHealingStyle;
    private final String stressReliefStyle;
    private final String personality;

    public static UserInfoDto from(User user) {
        return UserInfoDto.builder()
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
