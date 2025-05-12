package com.konkuk.strhat.domain.user.application;

import com.konkuk.strhat.domain.user.dao.UserRepository;
import com.konkuk.strhat.domain.user.entity.CustomUserDetails;
import com.konkuk.strhat.domain.user.entity.User;
import com.konkuk.strhat.domain.user.exception.NotFoundUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String kakaoId) throws UsernameNotFoundException {
        User user = userRepository.findByKakaoId(Long.valueOf(kakaoId))
                .orElseThrow(NotFoundUserException::new);

        return new CustomUserDetails(user.getId(), user.getKakaoId(), user.getNickname());
    }
}
