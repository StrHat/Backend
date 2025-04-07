package com.konkuk.strhat.ai.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GptRequest {
    private String model;
    private List<GptRequestMessage> input;
    private Double temperature;
}