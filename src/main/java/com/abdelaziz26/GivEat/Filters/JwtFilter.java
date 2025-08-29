package com.abdelaziz26.GivEat.Filters;

import com.abdelaziz26.GivEat.Configs.CustomUserDetailsService;
import com.abdelaziz26.GivEat.Utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final static Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {


        if(request.getRequestURI().contains("/api/auth")
                || request.getRequestURI().contains("/v3/api-docs")
                || request.getRequestURI().contains("/swagger-ui")
                || request.getRequestURI().contains("/swagger-ui.html")) {
            filterChain.doFilter(request, response);
            return;
        }

        String AuthHeader = request.getHeader("Authorization");

        if(AuthHeader == null || !AuthHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = AuthHeader.substring(7);

        String email = jwtUtil.extractUserName(token);

        if(email == null) {
            filterChain.doFilter(request, response);
            return;
        }

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        if(userDetails == null) {
            filterChain.doFilter(request, response);
            return;
        }

        if(!jwtUtil.validateToken(token, userDetails)) {
            filterChain.doFilter(request, response);
            return;
        }

        if(SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails,
                null,
                userDetails.getAuthorities());

        //auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(auth);

        logger.info("User {} authenticated successfully", email);
        logger.info("User authorities: {}", userDetails.getAuthorities());

        filterChain.doFilter(request, response);

    }

}
