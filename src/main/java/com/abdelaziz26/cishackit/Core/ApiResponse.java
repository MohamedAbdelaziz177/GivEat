package com.abdelaziz26.cishackit.Core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApiResponse<T> {

    private T data = null;

    private String message = null;

    private boolean success = true;

    public ApiResponse(T data, boolean success)
    {
        this.data = data;
        this.success = success;
    }

    private ApiResponse(String message, boolean success)
    {
        this.message = message;
        this.success = success;
    }


    public static <T> ApiResponse<T> createSuccessResponse(T data)
    {
        return new ApiResponse<>(data, true);
    }

    public static <T> ApiResponse<T> createFailureResponse(String message)
    {
        return new ApiResponse<>(message, false);
    }
}
