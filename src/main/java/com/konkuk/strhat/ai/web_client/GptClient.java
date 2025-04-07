package com.konkuk.strhat.ai.web_client;

import com.konkuk.strhat.ai.dto.GptReplyResult;
import com.konkuk.strhat.ai.dto.GptRequest;
import com.konkuk.strhat.ai.dto.GptResponse;
import com.konkuk.strhat.ai.prompt.GptPrompt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
public class GptClient {

    private final WebClient webClient;
    private final GptProperties gptProperties;

    public GptClient(@Qualifier("openAiWebClient") WebClient webClient, GptProperties gptProperties) {
        this.webClient = webClient;
        this.gptProperties = gptProperties;
    }

    public GptReplyResult chat(GptPrompt prompt) {
        GptRequest request = new GptRequest(
                gptProperties.getModel(),
                prompt.toMessages(),
                gptProperties.getTemperature()
        );

        GptResponse response = webClient.post()
                .uri("/responses")
                .headers(headers -> {
                    headers.setBearerAuth(gptProperties.getKey());
                    headers.add("OpenAI-Organization", gptProperties.getOrganization());
                    headers.add("OpenAI-Project", gptProperties.getProject());
                })
                .bodyValue(request)
                .retrieve()
                .bodyToMono(GptResponse.class)
                .block();

        if (response != null && !response.getOutput().isEmpty()) {
            GptResponse.Output out = response.getOutput().get(0);
            String status = out.getStatus();
            String type = out.getType();
            String text = out.getContent() != null && !out.getContent().isEmpty()
                    ? out.getContent().get(0).getText()
                    : "";
            return new GptReplyResult(status, type, text);
        }

        return new GptReplyResult("error", "none", "응답이 비어 있습니다.");
    }
}