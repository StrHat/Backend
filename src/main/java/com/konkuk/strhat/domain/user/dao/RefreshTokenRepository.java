package com.konkuk.strhat.domain.user.dao;

import com.konkuk.strhat.domain.user.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {

}
