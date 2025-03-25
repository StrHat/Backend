package com.konkuk.strhat.domain.user.dao;

import com.konkuk.strhat.domain.user.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {

    Optional<RefreshToken> findRefreshTokenByEmail(String email);

    void deleteAll(RefreshToken refreshToken);
}
