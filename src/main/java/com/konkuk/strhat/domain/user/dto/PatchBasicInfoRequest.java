package com.konkuk.strhat.domain.user.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
    @Min(1901)
    @Max(2155)
    private Integer birth;

    @NotBlank
    private String gender;

    @NotBlank
    private String job;
}
