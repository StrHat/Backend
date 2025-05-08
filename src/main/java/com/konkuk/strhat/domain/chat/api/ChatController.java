package com.konkuk.strhat.domain.chat.api;

import com.konkuk.strhat.domain.chat.application.AIChatService;
import com.konkuk.strhat.domain.chat.application.ChatService;
import com.konkuk.strhat.domain.chat.dto.ChatMessageLogResponse;
import com.konkuk.strhat.domain.chat.dto.ChatRequest;
import com.konkuk.strhat.domain.chat.dto.ChatResponse;
import com.konkuk.strhat.domain.chat.entity.ChatMessage;
import com.konkuk.strhat.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/diaries/{diaryId}/chat")
public class ChatController {

    private final ChatService chatService;
    private final AIChatService aiChatService;

    @PostMapping
    public ApiResponse<ChatResponse> chat(@PathVariable Long diaryId, @RequestBody ChatRequest request) {
        ChatMessage userChatMessage = chatService.saveUserChatMessage(diaryId, request);
        ChatMessage AIChatMessage = aiChatService.chat(userChatMessage);
        ChatResponse response = chatService.saveAIChatMessage(AIChatMessage);
        return ApiResponse.success(response);
    }
    @GetMapping
    public ApiResponse<ChatMessageLogResponse> getChatLog(@PathVariable Long diaryId) {
        ChatMessageLogResponse response = chatService.readChatLog(diaryId);
        return ApiResponse.success(response);
    }
}