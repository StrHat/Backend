package com.konkuk.strhat.domain.user.dao;

import com.konkuk.strhat.domain.user.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {

    Optional<RefreshToken> findByKakaoId(Long kakaoId);

    void deleteAll(RefreshToken refreshToken);
}
