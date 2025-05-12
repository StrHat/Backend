package com.konkuk.strhat.domain.user.dto;

import com.konkuk.strhat.domain.user.entity.User;
import com.konkuk.strhat.domain.user.enums.Gender;
import com.konkuk.strhat.domain.user.enums.Job;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostSignUpRequest {

    @NotNull
    private Long kakaoId;

    @NotBlank
    @Length(max = 5)
    private String nickname;

    @NotNull
    @Min(1901)
    @Max(2155)
    private Integer birth;

    @NotBlank
    private String gender;

    @NotBlank
    private String job;

    @NotBlank
    @Length(min = 20, max = 1000)
    private String hobbyHealingStyle;

    @NotBlank
    @Length(min = 20, max = 1000)
    private String stressReliefStyle;

    @NotBlank
    @Length(min = 20, max = 1000)
    private String personality;

    public User toUserEntity() {
        return User.builder()
                .kakaoId(this.kakaoId)
                .nickname(this.nickname)
                .birth(this.birth)
                .gender(Gender.from(this.gender))
                .job(Job.from(this.job))
                .hobbyHealingStyle(this.hobbyHealingStyle)
                .stressReliefStyle(this.stressReliefStyle)
                .personality(this.personality)
                .build();
    }
}
