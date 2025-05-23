package com.konkuk.strhat.domain.user.application;

import com.konkuk.strhat.domain.user.dao.RefreshTokenRepository;
import com.konkuk.strhat.domain.user.dto.TokenDto;
import com.konkuk.strhat.domain.user.entity.RefreshToken;
import com.konkuk.strhat.domain.user.exception.MissingTokenException;
import com.konkuk.strhat.domain.user.exception.NotFoundRefreshTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    private static final long ACCESS_TIME = 7 * 24 * 60 * 60 * 1000L;   // 1시간(임시로 7일)
    private static final long REFRESH_TIME = 30 * 24 * 60 * 60 * 1000L;    // 30일
    public static final String ACCESS_TOKEN = "Authorization";
    public static final String GRANT_TYPE = "Bearer ";

    private final RefreshTokenRepository refreshTokenRepository;
    private final CustomUserDetailsService customUserDetailsService;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String getHeaderToken(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (token == null || token.isBlank()) {
            throw new MissingTokenException();
        }

        if (token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return token;
    }

    public TokenDto createAllToken(Long kakaoId) {
        String accessToken = GRANT_TYPE + createAccessToken(kakaoId);
        String refreshToken = GRANT_TYPE + createRefreshToken(kakaoId);

        return TokenDto.of(accessToken, refreshToken);
    }

    public String createAccessToken(Long kakaoId) {
        Claims claims = Jwts.claims();
        claims.put("kakaoId", kakaoId);
        Date date = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(date.getTime() + ACCESS_TIME))
                .setIssuedAt(date)
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    public String createRefreshToken(Long kakaoId) {
        Claims claims = Jwts.claims();
        claims.put("kakaoId", kakaoId);
        Date date = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(date.getTime() + REFRESH_TIME))
                .setIssuedAt(date)
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    public boolean validateToken(String token) {
        try{
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException exception) {
            throw exception;
        } catch (UnsupportedJwtException
                 | MalformedJwtException
                 | SignatureException
                 | IllegalArgumentException exception) {
            throw new JwtException("");
        }
    }

    public Long getKakaoIdByToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("kakaoId", Long.class);
    }

    public RefreshToken validateRefreshToken(String refreshToken) {
        validateToken(refreshToken);
        Long kakaoId = getKakaoIdByToken(refreshToken);
        Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findByKakaoId(kakaoId);
        if (optionalRefreshToken.isEmpty()) {
            throw new NotFoundRefreshTokenException();
        }
        return optionalRefreshToken.get();
    }

    public Date getExpiredAt(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }

    public Authentication createAuthentication(String kakaoId) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(kakaoId);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public void setResponseHeaderToken(HttpServletResponse response, TokenDto tokenDto) {
        response.setHeader("Authorization", tokenDto.getAccessToken());
        response.setHeader("Refresh-Token", tokenDto.getRefreshToken());
    }
}
