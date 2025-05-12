package com.konkuk.strhat.domain.user.application;

import com.konkuk.strhat.domain.user.dao.UserRepository;
import com.konkuk.strhat.domain.user.dto.PostKakaoSignInRequest;
import com.konkuk.strhat.domain.user.dto.PostKakaoSignInResponse;
import com.konkuk.strhat.domain.user.dto.TokenDto;
import com.konkuk.strhat.domain.user.entity.User;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KakaoOAuthService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Transactional(readOnly = true)
    public PostKakaoSignInResponse getUserIdByToken(PostKakaoSignInRequest request, HttpServletResponse response) {
        Long kakaoId = getKakaoIdByToken(request.getKakaoAccessToken());

        Optional<User> user = userRepository.findByKakaoId(kakaoId);
        if(user.isPresent()){
            TokenDto tokenDto = jwtProvider.createAllToken(user.get().getKakaoId());
            jwtProvider.setResponseHeaderToken(response, tokenDto);
            return PostKakaoSignInResponse.of(true, kakaoId);
        }
        return PostKakaoSignInResponse.of(false, kakaoId);
    }

    private Long getKakaoIdByToken(String kakaoAccessToken) {
        Map<String, Object> result = WebClient.create()
                .get()
                .uri("https://kapi.kakao.com/v1/user/access_token_info")
                .headers(headers -> headers.setBearerAuth(kakaoAccessToken))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();

        return ((Number) result.get("id")).longValue();
    }
}
