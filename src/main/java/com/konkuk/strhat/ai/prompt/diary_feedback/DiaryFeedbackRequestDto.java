package com.konkuk.strhat.ai.prompt.diary_feedback;

import com.konkuk.strhat.domain.user.dto.UserInfo;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DiaryFeedbackRequestDto {

    @NotBlank(message = "userTraits는 필수입니다.")
    private final String userTraits;

    @NotBlank(message = "diaryContent는 필수입니다.")
    private final String diaryContent;

    public static DiaryFeedbackRequestDto of(UserInfo userInfo, String diaryContent) {
        return DiaryFeedbackRequestDto.builder()
                .userTraits(buildUserTraits(userInfo))
                .diaryContent(diaryContent)
                .build();
    }

    private static String buildUserTraits(UserInfo user) {
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
