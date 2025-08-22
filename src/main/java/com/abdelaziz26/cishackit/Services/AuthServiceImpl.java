package com.abdelaziz26.cishackit.Services;

import com.abdelaziz26.cishackit.Core.DTOs.Auth.*;
import com.abdelaziz26.cishackit.Core.Entities.Role;
import com.abdelaziz26.cishackit.Core.Entities.User;
import com.abdelaziz26.cishackit.Core.Exceptions.GlobalExceptionHandler;
import com.abdelaziz26.cishackit.Core.Interfaces.AuthService;
import com.abdelaziz26.cishackit.Core.Repositories.RoleRepo;
import com.abdelaziz26.cishackit.Core.Repositories.UserRepo;
import com.abdelaziz26.cishackit.Utils.JwtUtil;
import com.abdelaziz26.cishackit.Utils.MailService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtUtil jwtService;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepository;
    private final RoleRepo roleRepository;

    private final static Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Override
    public TokenResponse login(LoginDto loginDto, HttpServletResponse response) {

        Authentication authToken =
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

        Authentication authRes = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authRes);

        TokenResponse tokens = jwtService.getTokens(loginDto.getEmail());
        setRefreshTokenInCookie(response, tokens.getRefreshToken());

        return TokenResponse.builder()
                .accessToken(tokens.getAccessToken())
                .refreshToken(tokens.getRefreshToken())
                .accessTokenExpiry(tokens.getAccessTokenExpiry())
                .build();
    }

    @Override
    public String register(RegisterDto registerDto) {

        Optional<User> user = userRepository.findByEmail(registerDto.getEmail());

        if(user.isPresent())
            throw new AuthenticationServiceException("User already exists");

        if(!registerDto.getPassword().equals(registerDto.getConfirmPassword()))
            throw new AuthenticationServiceException("Passwords do not match");

        Role role = roleRepository.findByName("ROLE_USER").orElseThrow();

        User newUser = User.builder()
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .firstName(registerDto.getFirstName())
                .lastName(registerDto.getLastName())
                .role(role)
                .build();

        userRepository.save(newUser);

        Long otp = sendOtpToUser(registerDto.getEmail());

        newUser.setConfirmationOtp(otp);
        newUser.setConfirmationOtpExpiry(new Date(System.currentTimeMillis() + 1000 * 60 * 2));
        newUser.setVerified(false);

        userRepository.save(newUser);

        return "User Registered Successfully - check ur email for confirmation";
    }

    public TokenResponse refreshToken(HttpServletRequest request, HttpServletResponse response) {

        String refreshToken = getRefreshTokenFromCookie(request);
        TokenResponse tokenResponse =  jwtService.refreshToken(refreshToken);

        if(!tokenResponse.getSuccess())
            throw new NoSuchElementException("No such token found");

        setRefreshTokenInCookie(response, tokenResponse.getRefreshToken());

        return tokenResponse;

    }

    @Override
    public void resendOtp(String email) {

        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new NoSuchElementException("No such user found"));

        Long otp = sendOtpToUser(email);

        user.setConfirmationOtp(otp);
        user.setConfirmationOtpExpiry(new Date(System.currentTimeMillis() + 1000 * 60 * 2));

        userRepository.save(user);
    }

    @Override
    public void confirmUser(String email, Long otp) {

        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new NoSuchElementException("No such user found"));

        //logger.info(user.getConfirmationOtp() + "---" + otp + (otp.equals(user.getConfirmationOtp())) );

        if(!user.getConfirmationOtp().equals(otp))
            throw new AuthenticationServiceException("Invalid OTP");

        if(user.getConfirmationOtpExpiry().before(new Date()))
            throw new AuthenticationServiceException("OTP Expired");

        user.setConfirmationOtp(0L);
        user.setConfirmationOtpExpiry(new Date());


        userRepository.save(user);
    }

    public void forgetPassword(String email)
    {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new NoSuchElementException("No such user found"));

        Long otp = sendOtpToUser(email);

        user.setPasswordResetOtp(otp);
        user.setPasswordResetOtpExpiry(new Date(System.currentTimeMillis() + 1000 * 60 * 2));

        userRepository.save(user);
    }

    public void resetPassword(ResetPasswordDto request)
    {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() ->
                new NoSuchElementException("No such user found"));

        if(!user.getPasswordResetOtp().equals(request.getCode()))
            throw new AuthenticationServiceException("Invalid OTP");

        if(user.getPasswordResetOtpExpiry().before(new Date()))
            throw new AuthenticationServiceException("OTP Expired");

        if(!request.getPassword().equals(request.getConfirmPassword()))
            throw new AuthenticationServiceException("Confirm Password Mismatch");

        user.setPasswordResetOtp(0L);
        user.setPasswordResetOtpExpiry(new Date());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
    }
    private Long sendOtpToUser(String email) {

        Long otp = new Random().nextLong(100000, 199999);;

        mailService.sendSimpleMail(email, "OTP Code", "Your OTP Code is: " + otp);

        return otp;
    }

    private void setRefreshTokenInCookie(HttpServletResponse response, String refreshToken)
    {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/api/auth/refresh-token");
        cookie.setMaxAge(3600 * 24 * 15);

        response.addCookie(cookie);
    }
    private String getRefreshTokenFromCookie(HttpServletRequest request)
    {
        for(Cookie cookie : request.getCookies()) {
            if(cookie.getName().equals("refreshToken")) {
                return cookie.getValue();
            }
        }

        return null;
    }
}
