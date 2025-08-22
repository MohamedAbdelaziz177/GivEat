package com.abdelaziz26.cishackit.Core.DTOs.Auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResendConfirmationCodeRequest {

    @NotBlank(message = "Email is required")
    private String email;
}
