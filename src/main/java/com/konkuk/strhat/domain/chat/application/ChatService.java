package com.konkuk.strhat.domain.chat.application;

import com.konkuk.strhat.domain.chat.dto.ChatMessageLogResponse;
import com.konkuk.strhat.domain.chat.dto.ChatRequest;
import com.konkuk.strhat.domain.chat.dto.ChatResponse;
import com.konkuk.strhat.domain.chat.entity.ChatMessage;
import com.konkuk.strhat.domain.chat.enums.Sender;
import com.konkuk.strhat.domain.chat.dao.ChatMessageRepository;
import com.konkuk.strhat.domain.diary.entity.Diary;
import com.konkuk.strhat.domain.diary.dao.DiaryRepository;
import com.konkuk.strhat.domain.diary.exception.NotFoundDiaryException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final DiaryRepository diaryRepository;
    private final ChatMessageRepository chatMessageRepository;

    @Transactional
    public ChatMessage saveUserChatMessage(Long diaryId, ChatRequest request) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(NotFoundDiaryException::new);

        ChatMessage userMessage = ChatMessage.builder()
                .diary(diary)
                .sender(Sender.USER)
                .content(request.getUserMessage())
                .chatMode(request.getChatMode())
                .build();

        return saveChatMessages(userMessage);
    }

    @Transactional
    public ChatResponse saveAIChatMessage(ChatMessage AIMessage) {
        saveChatMessages(AIMessage);
        return ChatResponse.of(AIMessage.getContent());
    }

    @Transactional(readOnly = true)
    public ChatMessageLogResponse readChatLog(Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(NotFoundDiaryException::new);
        return ChatMessageLogResponse.from(chatMessageRepository.findAllByDiary(diary));
    }

    private ChatMessage saveChatMessages(ChatMessage chatMessage) {
        return chatMessageRepository.save(chatMessage);
    }

}
