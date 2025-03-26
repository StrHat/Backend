package com.konkuk.strhat.global.filter;

import com.konkuk.strhat.domain.user.application.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = jwtProvider.getHeaderToken(request);

        if(accessToken != null && jwtProvider.validateToken(accessToken)) {
            setAuthentication(jwtProvider.getEmailByToken(accessToken));
        }
        filterChain.doFilter(request, response);
    }

    public void setAuthentication(String email) {
        Authentication authentication = jwtProvider.createAuthentication(email);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
