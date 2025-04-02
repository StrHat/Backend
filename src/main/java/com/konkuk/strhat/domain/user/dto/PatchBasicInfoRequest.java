package com.konkuk.strhat.domain.user.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PatchBasicInfoRequest {
    @NotBlank
    private String nickname;

    @NotNull
    @Digits(integer = 4, fraction = 0)
    private Integer birth;

    @NotBlank
    private String gender;

    @NotBlank
    private String job;
}
