package com.abdelaziz26.cishackit.Controllers;

import com.abdelaziz26.cishackit.Core.DTOs.Auth.*;
import com.abdelaziz26.cishackit.Core.Interfaces.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginRequest,
                                                 HttpServletResponse response)
    {
        TokenResponse tokenResponse = authService.login(loginRequest, response);
        return ResponseEntity.ok(AuthResponseDto.builder()
                        .accessToken(tokenResponse.getAccessToken())
                        .expirationDate(tokenResponse.getAccessTokenExpiry())
                        .build());
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerRequest)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(registerRequest));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponseDto> refreshToken(HttpServletRequest request, HttpServletResponse response)
    {
        TokenResponse tokenResponse = authService.refreshToken(request, response);
        return ResponseEntity.ok(AuthResponseDto.builder()
                        .accessToken(tokenResponse.getAccessToken())
                        .expirationDate(null)
                        .build());
    }

    @PostMapping("/resend-confirmation-code")
    public ResponseEntity<String> resendConfirmationCode(@RequestBody ResendConfirmationCodeRequest request)
    {
        authService.resendOtp(request.getEmail());
        return ResponseEntity.ok("Confirmation Code Sent");
    }

    @PostMapping("/confirm-email")
    public ResponseEntity<?> confirmEmail(@RequestBody ConfirmEmailDto request)
    {
        //logger.info(request.getCode().toString());
        authService.confirmUser(request.getEmail(), request.getCode());
        return ResponseEntity.ok("Email Confirmed");
    }

    @PostMapping("/forget-password")
    public ResponseEntity<String> forgetPassword(@RequestBody Map<String, String> request)
    {
        authService.forgetPassword(request.get("email"));
        return ResponseEntity.ok("Password Reset Code Sent");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordDto request)
    {
        authService.resetPassword(request);
        return ResponseEntity.ok("Password Reset Successfully");
    }
}
