package com.konkuk.strhat.domain.chat.application;

import com.konkuk.strhat.ai.dto.GptReplyResult;
import com.konkuk.strhat.ai.prompt.chat.ChatPrompt;
import com.konkuk.strhat.ai.prompt.chat.ChatRequestDto;
import com.konkuk.strhat.ai.prompt.chat.ChatResponseDto;
import com.konkuk.strhat.ai.util.GptResponseParser;
import com.konkuk.strhat.ai.web_client.GptClient;
import com.konkuk.strhat.domain.chat.dao.ChatMessageRepository;
import com.konkuk.strhat.domain.chat.entity.ChatMessage;
import com.konkuk.strhat.domain.chat.enums.ChatMode;
import com.konkuk.strhat.domain.chat.enums.Sender;
import com.konkuk.strhat.domain.diary.entity.Diary;
import com.konkuk.strhat.domain.stressScore.exception.UnknownStressScoreGenerateException;
import com.konkuk.strhat.domain.user.dto.UserInfoDto;
import com.konkuk.strhat.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AIChatService {
    private final GptClient gptClient;
    private final GptResponseParser gptResponseParser;
    private final ChatMessageRepository chatMessageRepository;

    public ChatMessage chat(ChatMessage chatMessage){
        Diary diary = chatMessage.getDiary();
        List<ChatMessage> previousMessages = chatMessageRepository.findAllByDiary(diary);
        ChatMode mode = chatMessage.getChatMode();
        String currentMessage = chatMessage.getContent();
        return generateChatResponse(diary, previousMessages, mode, currentMessage);
    }

    private ChatMessage generateChatResponse(Diary diary, List<ChatMessage> previousMessages, ChatMode chatMode, String userChatMessage) {
        User user = diary.getUser();
        log.info("[AIChatService] 채팅 응답 생성 요청 시작, diaryId={}, userId={}", diary.getId(), user.getId());

        try {
            // 1. GPT API 요청에 필요한 DTO 구성
            log.info("[AIChatService] GPT API 요청에 필요한 DTO 구성 시작, diaryId={}, userId={}", diary.getId(), user.getId());
            ChatRequestDto promptRequest = ChatRequestDto.of(
                    UserInfoDto.from(diary.getUser()),
                    diary.getContent(),
                    previousMessages,
                    userChatMessage
            );

            // 2. Prompt 생성 및 요청
            log.info("[AIChatService] 프롬프트 생성 시작, diaryId={}, userId={}", diary.getId(), user.getId());
            ChatPrompt prompt = new ChatPrompt(promptRequest);
            log.info("[AIChatService] GPT 요청 시작, diaryId={}, userId={}", diary.getId(), user.getId());
            GptReplyResult result = gptClient.call(prompt);


            // 3. GPT 응답 파싱 및 유효성 검증
            log.info("[AIChatService] GPT 응답 파싱 시작, diaryId={}, userId={}", diary.getId(), user.getId());
            ChatResponseDto chatResponseDto = gptResponseParser.parse(result, ChatResponseDto.class);
            log.info("[AIChatService] GPT 응답 유효성 검증 시작, diaryId={}, userId={}", diary.getId(), user.getId());
            chatResponseDto.validateResult();

            // 4. StressScore 객체 생성 및 반환
            log.info("[AIChatService] 채팅 응답 ChatMessage 객체 생성 시작, diaryId={}, userId={}", diary.getId(), user.getId());
            ChatMessage chatMessage = ChatMessage.builder()
                    .content(chatResponseDto.getChatResponse())
                    .sender(Sender.CHAT_BOT)
                    .diary(diary)
                    .chatMode(chatMode)
                    .build();
            log.info("[AIChatService] 채팅 응답 생성 완료, diaryId={}, userId={}", diary.getId(), user.getId());
            return chatMessage;

        } catch (Exception e) {
            log.error("[AIChatService] 채팅 응답 생성 실패, diaryId={}, userId={}", diary.getId(), user.getId());
            throw new UnknownStressScoreGenerateException(e.getMessage());
        }
    }
}

