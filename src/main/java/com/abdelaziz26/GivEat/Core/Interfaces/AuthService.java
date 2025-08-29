package com.abdelaziz26.GivEat.Core.Interfaces;

import com.abdelaziz26.GivEat.DTOs.Auth.LoginDto;
import com.abdelaziz26.GivEat.DTOs.Auth.RegisterDto;
import com.abdelaziz26.GivEat.DTOs.Auth.ResetPasswordDto;
import com.abdelaziz26.GivEat.DTOs.Auth.TokenResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService  {

    TokenResponse login(LoginDto loginDto, HttpServletResponse response);
    TokenResponse register(RegisterDto registerDto);
    TokenResponse refreshToken(HttpServletRequest request, HttpServletResponse response);
    void resendOtp(String email);
    void confirmUser(String email, Long otp);
    void forgetPassword(String email);
    void resetPassword(ResetPasswordDto request);
}
