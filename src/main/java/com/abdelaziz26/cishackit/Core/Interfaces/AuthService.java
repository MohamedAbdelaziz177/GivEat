package com.abdelaziz26.cishackit.Core.Interfaces;

import com.abdelaziz26.cishackit.Core.DTOs.Auth.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService  {

    TokenResponse login(LoginDto loginDto, HttpServletResponse response);
    String register(RegisterDto registerDto);
    TokenResponse refreshToken(HttpServletRequest request, HttpServletResponse response);
    void resendOtp(String email);
    void confirmUser(String email, Long otp);
    void forgetPassword(String email);
    void resetPassword(ResetPasswordDto request);
}
