package com.monopoly.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ApiResponse<T> {
    private boolean successful;
    private String message;
    private T data;

    public ApiResponse(boolean successful, T data) {
        this.successful = successful;
        this.data = data;
    }

    public ApiResponse(boolean successful, String message) {
        this.successful = successful;
        this.message = message;
    }

    public ApiResponse(boolean successful, String message, T data) {
        this.successful = successful;
        this.message = message;
        this.data = data;
    }


    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(true, "Operation Successful", data);
    }

    public static <T> ApiResponse<T> ok(String message, T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccessful(true);
        response.setMessage(message);
        response.setData(data);
        return response;
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message);
    }
}