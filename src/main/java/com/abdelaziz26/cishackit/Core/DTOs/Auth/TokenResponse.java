package com.abdelaziz26.cishackit.Core.DTOs.Auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TokenResponse {

    private String accessToken;

    private String refreshToken;

    private Boolean success = true;

    private String error = "";

    private Date accessTokenExpiry = new Date(System.currentTimeMillis() + 1000 * 60  * 15);
}
