package com.konkuk.strhat.ai.web_client;

import com.konkuk.strhat.ai.dto.GptReplyResult;
import com.konkuk.strhat.ai.dto.GptRequest;
import com.konkuk.strhat.ai.dto.GptResponse;
import com.konkuk.strhat.ai.prompt.GptPrompt;
import org.springframework.retry.support.RetrySynchronizationManager;
import io.netty.handler.timeout.TimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import com.konkuk.strhat.ai.exception.GptResponseParseException;

import java.io.IOException;

@Slf4j
@Component

public class GptClient {
    private final WebClient webClient;
    private final GptProperties gptProperties;
    private static final int MAX_ATTEMPTS = 3;
    private static final String HEADER_OPENAI_ORGANIZATION = "OpenAI-Organization";
    private static final String HEADER_OPENAI_PROJECT = "OpenAI-Project";

    public GptClient(@Qualifier("openAiWebClient") WebClient webClient, GptProperties gptProperties) {
        this.webClient = webClient;
        this.gptProperties = gptProperties;
    }

    @Retryable(
            retryFor = { IOException.class, WebClientException.class, TimeoutException.class},
            maxAttempts = MAX_ATTEMPTS,
            backoff = @Backoff(delay = 1000) // 각 재시도 사이 1초 간격
    )
    public GptReplyResult call(GptPrompt prompt) {
        logBeforeCall(prompt);
        GptRequest request = createRequest(prompt);
        GptResponse response = sendRequest(request);
        logAfterCall();
        return parseResponse(response);
    }

    private void logBeforeCall(GptPrompt prompt) {
        int currentAttempt = RetrySynchronizationManager.getContext() != null
                ? RetrySynchronizationManager.getContext().getRetryCount() + 1
                : 1;

        log.info("[GptClient] GPT 요청 전송, {} - 현재 시도 횟수={}",
                prompt.getClass().getSimpleName(), currentAttempt);
    }

    private void logAfterCall() {
        int currentAttempt = RetrySynchronizationManager.getContext() != null
                ? RetrySynchronizationManager.getContext().getRetryCount() + 1
                : 1;
        log.info("[GptClient] GPT 응답 수신 완료 - 총 시도 횟수={}", currentAttempt);
    }

    private GptRequest createRequest(GptPrompt prompt) {
        return new GptRequest(
                gptProperties.getModel(),
                prompt.toMessages(),
                gptProperties.getTemperature()
        );
    }

    private GptResponse sendRequest(GptRequest request) {
        return webClient.post()
                .uri("/responses")
                .headers(headers -> {
                    headers.setBearerAuth(gptProperties.getKey());
                    headers.add(HEADER_OPENAI_ORGANIZATION, gptProperties.getOrganization());
                    headers.add(HEADER_OPENAI_PROJECT, gptProperties.getProject());
                })
                .bodyValue(request)
                .retrieve()
                .bodyToMono(GptResponse.class)
                .block();
    }

    private GptReplyResult parseResponse(GptResponse response) {
        if (response == null || response.getOutput() == null || response.getOutput().isEmpty()) {
            throw new GptResponseParseException("GPT 응답이 null이거나 output이 비어 있습니다.");
        }

        GptResponse.Output output = response.getOutput().get(0);
        String status = output.getStatus();
        String type = output.getType();
        String text = output.getContent() != null && !output.getContent().isEmpty()
                ? output.getContent().get(0).getText()
                : "";

        return new GptReplyResult(status, type, text);
    }

    @Recover
    public GptReplyResult recover(Exception e) throws Exception {
        log.error("GPT 호출 재시도 모두 실패, [{}]: {}", e.getClass(), e.getMessage());
        throw e;
    }

}