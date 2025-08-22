package com.abdelaziz26.cishackit.Utils;

import com.abdelaziz26.cishackit.Core.DTOs.Auth.TokenResponse;
import com.abdelaziz26.cishackit.Core.Entities.RefreshToken;
import com.abdelaziz26.cishackit.Core.Entities.User;
import com.abdelaziz26.cishackit.Core.Repositories.RefreshTokenRepo;
import com.abdelaziz26.cishackit.Core.Repositories.UserRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtUtil {

    @Value("${JWT_SEC}")
    private String secret;

    @Value("${spring.security.jwt.expiration}")
    private String accessTokenExpiration;

    @Value("${spring.security.jwt.refresh-token.expiration}")
    private String refreshTokenExpiration;

    private final UserRepo userRepo;

    private final RefreshTokenRepo refreshTokenRepo;

    public String generateToken(UserDetails userDetails, HashMap<String, Object> claims) {

        claims.put("authorities",
                userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()));

        return   Jwts.builder()
                .subject(userDetails.getUsername())
                .claims()
                .add(claims)
                .issuedAt(new Date())
                .expiration(getExpiry())
                .and()
                .signWith(getKey())
                .compact();

    }

    public boolean validateToken(String token, UserDetails userDetails)
    {
        return !isExpired(token) && extractUserName(token).equals(userDetails.getUsername());
    }

    // just to implement DRY
    public Key getKey(){

        byte[] keyBytes = Base64.getDecoder().decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    // Verify with takes SecretKey not Key :(
    public SecretKey getSecretKey(){

        byte[] keyBytes = Base64.getDecoder().decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Date getExpiry(){
        return new Date(System.currentTimeMillis() + Long.parseLong(accessTokenExpiration));
    }

    public boolean isExpired(String token) {
        return extractExpiration(token).before(new Date(System.currentTimeMillis()));
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    // ---- REFRESH TOKEN RELATED METHODS ------

    @Transactional
    public TokenResponse refreshToken(String tok) {

        RefreshToken validToken = validateRefreshToken(tok);

        TokenResponse tokenResponse = new TokenResponse();

        if (validToken == null) {
            tokenResponse.setSuccess(Boolean.FALSE);
            return tokenResponse;
        }

        if(validToken.isRevoked())
        {
            /* Token Rotation  -- Indicates Hijacking */

            killTokensForUser(validToken.getUser().getId());
            return TokenResponse.builder()
                    .success(false)
                    .error("Refresh token is revoked - please re-login")
                    .build();
        }

        User user = validToken.getUser();
        tokenResponse = getTokens(user);

        validToken.setRevoked(true);
        refreshTokenRepo.save(validToken);

        return tokenResponse;

    }

    @Transactional
    public RefreshToken generateRefreshToken(Long userId){

        User user = userRepo.findById(userId).orElseThrow(() ->
                new RuntimeException("User not found"));

        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setToken(UUID.randomUUID().toString() + "_" + UUID.randomUUID().toString());
        refreshToken.setRevoked(false);
        refreshToken.setExpires(new Date(System.currentTimeMillis() + Long.parseLong(refreshTokenExpiration)));
        refreshToken.setUser(user);

        refreshTokenRepo.save(refreshToken);

        return refreshToken;
    }

    public RefreshToken validateRefreshToken(String refreshToken) {

        Optional<RefreshToken> refTok = refreshTokenRepo.findByToken(refreshToken);

        if(refTok.isEmpty() || refTok.get().getExpires().before(new Date()))
            return null;

        return refTok.get();
    }

    public TokenResponse getTokens(User user)
    {
        TokenResponse tokenResponse = new TokenResponse();

        tokenResponse.setAccessToken(generateToken(user,new HashMap<>()));
        tokenResponse.setRefreshToken(generateRefreshToken(user.getId()).getToken());
        tokenResponse.setSuccess(Boolean.TRUE);

        return tokenResponse;
    }

    public TokenResponse getTokens(String email)
    {
        User user = userRepo.findByEmail(email).orElse(null);
        return getTokens(user);
    }
    private void killTokensForUser(Long userId) {

        refreshTokenRepo.deleteAllByUser_Id(userId);
    }
}
