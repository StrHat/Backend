package com.konkuk.strhat.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    @JsonProperty(value = "isSuccess")
    private final boolean success;

    @JsonProperty(value = "response")
    private final T response;

    @JsonProperty(value = "error")
    private final ErrorResponse errorResponse;

    public static <T> ApiResponse<T> success(T response) {
        return ApiResponse.<T>builder()
                .success(true)
                .response(response)
                .build();
    }

    public static ApiResponse<?> error(ErrorResponse errorResponse) {
        return ApiResponse.builder()
                .success(false)
                .errorResponse(errorResponse)
                .build();
    }

    public static ApiResponse<Void> successOnly() {
        return ApiResponse.<Void>builder()
                .success(true)
                .build();
    }
}
