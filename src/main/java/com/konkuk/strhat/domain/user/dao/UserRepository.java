package com.konkuk.strhat.domain.user.dao;

import com.konkuk.strhat.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByKakaoId(Long kakaoId);
    Optional<User> findById(Long id);
    Optional<User> findByKakaoId(Long kakaoId);
}
