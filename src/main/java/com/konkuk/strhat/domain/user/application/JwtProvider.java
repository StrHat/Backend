package com.konkuk.strhat.domain.user.application;

import com.konkuk.strhat.domain.user.dao.RefreshTokenRepository;
import com.konkuk.strhat.domain.user.dto.TokenDto;
import com.konkuk.strhat.domain.user.entity.RefreshToken;
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
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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

    private static final long ACCESS_TIME = 60 * 60 * 1000L;   // 1시간
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
        if (token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return token;   //예외 던지기
    }

    public TokenDto createAllToken(String email) {
        String accessToken = GRANT_TYPE + createAccessToken(email);
        String refreshToken = GRANT_TYPE + createRefreshToken(email);
        Instant refreshTokenExpiredAt = Instant.now().plus(30, ChronoUnit.DAYS);

        return new TokenDto(accessToken, refreshToken, refreshTokenExpiredAt);
    }

    public String createAccessToken(String email) {
        Claims claims = Jwts.claims();
        claims.put("email", email);
        Date date = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(date.getTime() + ACCESS_TIME))
                .setIssuedAt(date)
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    public String createRefreshToken(String email) {
        Claims claims = Jwts.claims();
        claims.put("email", email);
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
        } catch (UnsupportedJwtException
                 | MalformedJwtException
                 | SignatureException
                 | ExpiredJwtException
                 | IllegalArgumentException exception) {
            throw new JwtException("");
        }
    }

    public String getEmailByToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("email", String.class);
    }

    public Boolean validateRefreshToken(String refreshToken) {
        validateToken(refreshToken);
        String email = getEmailByToken(refreshToken);
        Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findByEmail(email);
        return optionalRefreshToken.isPresent();
    }

    public Date getExpiredAt(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }

    public Authentication createAuthentication(String email) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
