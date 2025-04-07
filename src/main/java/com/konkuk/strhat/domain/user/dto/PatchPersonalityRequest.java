package com.konkuk.strhat.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PatchPersonalityRequest {
    @NotBlank
    @Length(min = 20, max = 1000)
    private String personality;
}
