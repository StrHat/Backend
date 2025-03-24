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
    public PostKakaoSignInResponse getUserProfileByToken(PostKakaoSignInRequest request, HttpServletResponse httpServletResponse) {
        Map<String, Object> userAttributes = getUserAttributesByToken(request.getKakaoAccessToken());
        Map<String, Object> account = (Map<String, Object>) userAttributes.get("kakao_account");
        String email = (String) account.get("email");

        Optional<User> user = userRepository.findByEmail(email);

        if(user.isPresent()){
            TokenDto tokenDto = jwtProvider.createAllToken(user.get().getEmail());
            jwtProvider.setResponseHeaderToken(httpServletResponse, tokenDto);
            return new PostKakaoSignInResponse(true, email);
        }
        return new PostKakaoSignInResponse(false, email);
    }

    private Map<String, Object> getUserAttributesByToken(String kakaoToken){
        return WebClient.create()
                .get()
                .uri("https://kapi.kakao.com/v2/user/me")
                .headers(httpHeaders -> httpHeaders.setBearerAuth(kakaoToken))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
    }
}
