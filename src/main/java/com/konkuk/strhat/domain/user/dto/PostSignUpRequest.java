package com.konkuk.strhat.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor
public class PostSignUpRequest {

    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "닉네임은 필수 입력값입니다.")
    private String nickname;

    @NotNull(message = "출생년도는 필수 입력값입니다.")
    @Min(1000)
    @Max(9999)
    private Integer birth;

    @NotBlank(message = "성별은 필수 입력값입니다.")
    private String gender;

    @NotBlank(message = "직업은 필수 입력값입니다.")
    private String job;

    @NotBlank(message = "취미 및 힐링 방법은 필수 입력값입니다.")
    @Length(min = 20, max = 1000, message = "취미 및 힐링 방법은 20자 이상 1000자 이하이어야 합니다.")
    private String hobbyHealingStyle;

    @NotBlank(message = "스트레스 해소 방법은 필수 입력값입니다.")
    @Length(min = 20, max = 1000, message = "스트레스 해소 방법은 20자 이상 1000자 이하이어야 합니다.")
    private String stressReliefStyle;

    @NotBlank(message = "성향은 필수 입력값입니다.")
    @Length(min = 20, max = 1000, message = "성향은 20자 이상 1000자 이하이어야 합니다.")
    private String personality;
}
