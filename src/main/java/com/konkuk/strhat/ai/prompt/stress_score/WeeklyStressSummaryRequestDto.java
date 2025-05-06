package com.konkuk.strhat.ai.prompt.stress_score;

import com.konkuk.strhat.domain.user.dto.UserInfoDto;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class WeeklyStressSummaryRequestDto {

    @NotBlank(message = "userTraits는 필수입니다.")
    private final String userTraits;

    @NotBlank(message = "diaryContents는 필수입니다.")
    private final List<String> diaryContents;

    private final String chatLog;

    public static WeeklyStressSummaryRequestDto of(UserInfoDto userInfoDto, List<String> diaryContents) {
        return WeeklyStressSummaryRequestDto.builder()
                .userTraits(buildUserTraits(userInfoDto))
                .diaryContents(diaryContents)
                .chatLog("임시")
                .build();
    }

    private static String buildUserTraits(UserInfoDto user) {
        return String.format(
                "[닉네임]: %s, [출생년도]: %d, [성별]: %s, [직업]: %s, [취미 및 힐링 방법]: %s, [스트레스 해소 스타일]: %s, [성격]: %s",
                user.getNickname(),
                user.getBirth(),
                user.getGender(),
                user.getJob(),
                user.getHobbyHealingStyle(),
                user.getStressReliefStyle(),
                user.getPersonality()
        );
    }
}

