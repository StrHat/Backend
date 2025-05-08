package com.konkuk.strhat.ai.prompt.chat;

import com.konkuk.strhat.domain.chat.entity.ChatMessage;
import com.konkuk.strhat.domain.user.dto.UserInfoDto;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ChatRequestDto {

    @NotBlank(message = "userTraits는 필수입니다.")
    private final String userTraits;

    @NotBlank(message = "diaryContent는 필수입니다.")
    private final String diaryContent;

    private final List<String> chatLog;

    @NotBlank(message = "chatMode는 필수입니다.")
    private final String chatMode;

    @NotBlank(message = "userChatMessage는 필수입니다.")
    private final String userChatMessage;

    public static ChatRequestDto of(UserInfoDto userInfoDto, String diaryContent, List<ChatMessage> chatMessageList, String userChatMessage) {
        List<String> chatLog = new ArrayList<>();
        String chatMode = "";
        for (ChatMessage chatMessage : chatMessageList){
            chatLog.add(chatMessage.getSender().getDescription() + ": " + chatMessage.getContent());
            chatMode = chatMessage.getChatMode().getDescription();
        }

        return ChatRequestDto.builder()
                .userTraits(buildUserTraits(userInfoDto))
                .diaryContent(diaryContent)
                .chatLog(chatLog)
                .chatMode(chatMode)
                .userChatMessage(userChatMessage)
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
