package com.konkuk.strhat.domain.user.entity;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "refreshToken",timeToLive = 60 * 60 * 24 * 30)
public class RefreshToken {

    @Id
    private String id;

    private String refreshToken;

    @Indexed
    private Long kakaoId;

    @Builder
    public RefreshToken(String refreshToken, Long kakaoId) {
        this.refreshToken = refreshToken;
        this.kakaoId = kakaoId;
    }

    public RefreshToken updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }
}
