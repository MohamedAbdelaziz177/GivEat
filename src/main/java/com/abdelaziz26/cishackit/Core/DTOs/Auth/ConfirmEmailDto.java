package com.abdelaziz26.cishackit.Core.DTOs.Auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConfirmEmailDto {

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Code is required")
    private Long code;
}
