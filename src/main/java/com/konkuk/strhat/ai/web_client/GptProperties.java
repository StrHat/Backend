package com.konkuk.strhat.ai.web_client;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@Getter
@ConfigurationProperties(prefix = "openai.api")
public class GptProperties {
    private final String key;
    private final String model;
    private final Double temperature;
    private final Integer maxTokens;
    private final String organization;
    private final String project;

    @ConstructorBinding
    public GptProperties(String key, String model, Double temperature, Integer maxTokens, String organization, String project) {
        this.key = key;
        this.model = model;
        this.temperature = temperature;
        this.maxTokens = maxTokens;
        this.organization = organization;
        this.project = project;
    }
}