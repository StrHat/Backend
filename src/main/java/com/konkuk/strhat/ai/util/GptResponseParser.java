package com.konkuk.strhat.ai.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.konkuk.strhat.ai.exception.GptResponseParseException;
import com.konkuk.strhat.ai.dto.GptReplyResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GptResponseParser {

    private final ObjectMapper objectMapper;

    /**
     * GPT 응답 text를 특정 DTO로 파싱
     * @param result GptReplyResult (status, type, text 포함)
     * @param valueType 변환할 클래스 타입
     * @return 파싱된 객체 or null
     */
    public <T> T parse(GptReplyResult result, Class<T> valueType) {
        try {
            String cleanText = sanitize(result.getText());
            return objectMapper.readValue(cleanText, valueType);
        } catch (Exception e) {
            log.error("[GPT 응답 파싱 실패] text: {}", result.getText(), e);
            throw new GptResponseParseException(e.getMessage());
        }
    }

    // GPT가 반환하는 ```json ... ``` 같은 마크다운 제거
    private String sanitize(String text) {
        if (text.startsWith("```")) {
            int start = text.indexOf("{");
            int end = text.lastIndexOf("}");
            if (start >= 0 && end >= 0 && end >= start) {
                return text.substring(start, end + 1);
            }
        }
        return text;
    }

}