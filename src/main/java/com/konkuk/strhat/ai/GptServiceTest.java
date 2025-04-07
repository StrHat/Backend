package com.konkuk.strhat.ai;

import com.konkuk.strhat.ai.dto.GptReplyResult;
import com.konkuk.strhat.ai.prompt.GptPrompt;
import com.konkuk.strhat.ai.util.GptResponseParser;
import com.konkuk.strhat.ai.web_client.GptClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GptServiceTest {

    private final GptClient gptClient;
    private final GptResponseParser gptResponseParser;

    public <T> T requestAndParse(GptPrompt prompt, Class<T> valueType) {
        GptReplyResult result = gptClient.chat(prompt);
        return gptResponseParser.parse(result, valueType);
    }
}