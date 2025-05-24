package com.konkuk.strhat.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "isSuccess", "response" })
public class ApiResponseTempOnly {

    @JsonProperty("isSuccess")
    private final boolean success = true;

    @JsonProperty("response")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private final Object response = null;

    private ApiResponseTempOnly() {}

    public static ApiResponseTempOnly successTempOnly() {
        return new ApiResponseTempOnly();
    }
}
