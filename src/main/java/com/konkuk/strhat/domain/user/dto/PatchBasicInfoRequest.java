package com.konkuk.strhat.domain.user.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PatchBasicInfoRequest {
    private String nickname;
    private Integer birth;
    private String gender;
    private String job;
}
