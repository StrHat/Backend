package com.konkuk.strhat.global.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.konkuk.strhat.domain.user.exception.MissingTokenException;
import com.konkuk.strhat.global.error.ErrorCode;
import com.konkuk.strhat.global.response.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Slf4j
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            chain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            setErrorResponse(response, ErrorCode.TOKEN_EXPIRED);
        } catch (MissingTokenException e) {
            setErrorResponse(response, ErrorCode.MISSING_TOKEN);
        }catch (JwtException e) {
            setErrorResponse(response, ErrorCode.INVALID_TOKEN);
        }
    }

    private void setErrorResponse(HttpServletResponse response, ErrorCode errorCode) {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(errorCode.getHttpStatus().value());
        response.setContentType("application/json;charset=UTF-8");
        ErrorResponse errorResponse = ErrorResponse.fromErrorCode(errorCode);
        try{
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        }catch (IOException e){
            log.error(e.getMessage());
        }
    }

}
