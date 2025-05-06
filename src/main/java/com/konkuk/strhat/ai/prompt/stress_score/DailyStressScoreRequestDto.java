package com.konkuk.strhat.ai.prompt.stress_score;

import com.konkuk.strhat.domain.user.dto.UserInfoDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DailyStressScoreRequestDto {

    @NotBlank(message = "userTraits는 필수입니다.")
    private final String userTraits;

    @NotBlank(message = "diaryContent는 필수입니다.")
    private final String diaryContent;

    private final String chatLog;

    public static DailyStressScoreRequestDto of(UserInfoDto userInfoDto, String diaryContent) {
        return DailyStressScoreRequestDto.builder()
                .userTraits(buildUserTraits(userInfoDto))
                .diaryContent(diaryContent)
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

