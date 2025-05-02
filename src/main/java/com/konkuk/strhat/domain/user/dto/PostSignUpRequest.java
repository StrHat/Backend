package com.konkuk.strhat.domain.user.dto;

import jakarta.validation.constraints.Email;
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

    @NotBlank
    @Email
    private String email;

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
}
