package com.konkuk.strhat.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"success", "response"})
public class ApiResponse<T> {

    @JsonProperty(value = "isSuccess")
    private final boolean success = true;

    @JsonProperty(value = "response")
    private final T response;

    public static <T> ApiResponse<T> success(T response) {
        return ApiResponse.<T>builder()
                .response(response)
                .build();
    }

    public static ApiResponse<Void> successOnly() {
        return ApiResponse.<Void>builder()
                .build();
    }
}
