package com.abdelaziz26.cishackit.Core.DTOs.Auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResetPasswordDto {

    @NotBlank(message = "Email field is required")
    @Email(message = "Enter Valid email address")
    private String email;

    @NotBlank(message = "Password field is required")
    private String password;

    @NotBlank(message = "Password field is required")
    private String confirmPassword;

    @NotBlank(message = "Code is Required")
    private Long code;
}
