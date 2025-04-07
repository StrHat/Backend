package com.konkuk.strhat.ai.web_client;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "openai.api")
public class GptProperties {
    private String key;
    private String model;
    private Double temperature;
    private Integer maxTokens;
    private String organization;
    private String project;
}