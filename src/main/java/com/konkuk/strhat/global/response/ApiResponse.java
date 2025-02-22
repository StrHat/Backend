package com.konkuk.strhat.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    @JsonProperty(value = "isSuccess")
    private final boolean isSuccess;

    @JsonProperty(value = "response")
    private final T response;

    @JsonProperty(value = "error")
    private final ErrorResponse errorResponse;

    public static <T> ApiResponse<T> success(T response) {
        return new ApiResponse<>(true, response, null);
    }

    public static ApiResponse<?> error(ErrorResponse response) {
        return new ApiResponse<>(true, response, null);
    }

    public static ApiResponse<Void> successOnly() {
        return new ApiResponse<>(true, null, null);
    }
}
