package com.konkuk.strhat.domain.user.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

@Getter
@NoArgsConstructor
@RedisHash(value = "refreshToken",timeToLive = 60 * 60 * 24 * 30)
public class RefreshToken {
    private Long id;
    private String refreshToken;
    private String email;
}
