package com.abdelaziz26.GivEat.Controllers;

import com.abdelaziz26.GivEat.Core.Interfaces.AuthService;
import com.abdelaziz26.GivEat.DTOs.Auth.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Authentication", description = "Endpoints for user authentication and account management")
public class AuthController {

    private final AuthService authService;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    /**
     * Authenticate user with email and password.
     *
     * @param loginRequest contains email and password
     * @param response HttpServletResponse used to set refresh token cookie
     * @return AuthResponseDto containing access token and its expiration date
     */
    @Operation(summary = "Login user", description = "Logs in a user and returns an access token.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginRequest,
                                                 HttpServletResponse response) {
        TokenResponse tokenResponse = authService.login(loginRequest, response);
        return ResponseEntity.ok(AuthResponseDto.builder()
                .accessToken(tokenResponse.getAccessToken())
                .expirationDate(tokenResponse.getAccessTokenExpiry())
                .build());
    }

    /**
     * Register a new user account.
     *
     * @param registerRequest registration details (email, password, role, etc.)
     * @return message indicating successful registration
     */
    @Operation(summary = "Register new user", description = "Registers a new user and sends confirmation email.")
    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@RequestBody RegisterDto registerRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(registerRequest));
    }

    /**
     * Refresh JWT tokens using the refresh token stored in cookies.
     *
     * @param request contains refresh token cookie
     * @param response updated response with new refresh token cookie
     * @return AuthResponseDto containing new access token
     */
    @Operation(summary = "Refresh access token", description = "Generates a new access token using refresh token cookie.")
    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponseDto> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        TokenResponse tokenResponse = authService.refreshToken(request, response);
        return ResponseEntity.ok(AuthResponseDto.builder()
                .accessToken(tokenResponse.getAccessToken())
                .expirationDate(tokenResponse.getAccessTokenExpiry())
                .build());
    }

    /**
     * Resend the confirmation code (OTP) to the user's email.
     *
     * @param request contains user email
     * @return success message
     */
    @Operation(summary = "Resend confirmation code", description = "Resends OTP for email verification.")
    @PostMapping("/resend-confirmation-code")
    public ResponseEntity<String> resendConfirmationCode(@RequestBody ResendConfirmationCodeRequest request) {
        authService.resendOtp(request.getEmail());
        return ResponseEntity.ok("Confirmation Code Sent");
    }

    /**
     * Confirm user email with OTP code.
     *
     * @param request contains email and confirmation code
     * @return success message
     */
    @Operation(summary = "Confirm email", description = "Verifies user email using OTP code.")
    @PostMapping("/confirm-email")
    public ResponseEntity<?> confirmEmail(@RequestBody ConfirmEmailDto request) {
        authService.confirmUser(request.getEmail(), request.getCode());
        return ResponseEntity.ok("Email Confirmed");
    }

    /**
     * Send password reset OTP to user's email.
     *
     * @param request contains user email
     * @return success message
     */
    @Operation(summary = "Forgot password", description = "Sends OTP to user's email for password reset.")
    @PostMapping("/forget-password")
    public ResponseEntity<String> forgetPassword(@RequestBody Map<String, String> request) {
        authService.forgetPassword(request.get("email"));
        return ResponseEntity.ok("Password Reset Code Sent");
    }

    /**
     * Reset user password using OTP.
     *
     * @param request contains email, OTP code, and new password
     * @return success message
     */
    @Operation(summary = "Reset password", description = "Resets password using OTP sent to user's email.")
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordDto request) {
        authService.resetPassword(request);
        return ResponseEntity.ok("Password Reset Successfully");
    }
}


