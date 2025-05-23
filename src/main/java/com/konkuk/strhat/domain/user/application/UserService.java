package com.konkuk.strhat.domain.user.application;

import com.konkuk.strhat.domain.user.dao.RefreshTokenRepository;
import com.konkuk.strhat.domain.user.dao.UserRepository;
import com.konkuk.strhat.domain.user.dto.GetUserInfoResponse;
import com.konkuk.strhat.domain.user.dto.PatchBasicInfoRequest;
import com.konkuk.strhat.domain.user.dto.PatchHobbyHealingStyleRequest;
import com.konkuk.strhat.domain.user.dto.PatchPersonalityRequest;
import com.konkuk.strhat.domain.user.dto.PatchStressReliefStyleRequest;
import com.konkuk.strhat.domain.user.dto.PostReissueTokenRequest;
import com.konkuk.strhat.domain.user.dto.PostSignUpRequest;
import com.konkuk.strhat.domain.user.dto.TokenDto;
import com.konkuk.strhat.domain.user.entity.RefreshToken;
import com.konkuk.strhat.domain.user.entity.User;
import com.konkuk.strhat.domain.user.enums.Gender;
import com.konkuk.strhat.domain.user.enums.Job;
import com.konkuk.strhat.domain.user.exception.DuplicateKakaoIdException;
import com.konkuk.strhat.domain.user.exception.NotFoundUserException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public TokenDto createUser(PostSignUpRequest request, HttpServletResponse httpServletResponse) {

        if (userRepository.existsByKakaoId(request.getKakaoId())) {
            throw new DuplicateKakaoIdException();
        }

        User user = request.toUserEntity();
        userRepository.save(user);

        TokenDto tokenDto = jwtProvider.createAllToken(request.getKakaoId());
        RefreshToken refreshToken = new RefreshToken(tokenDto.getRefreshToken(), request.getKakaoId());
        refreshTokenRepository.save(refreshToken);
        jwtProvider.setResponseHeaderToken(httpServletResponse, tokenDto);
        return tokenDto;
    }

    @Transactional(readOnly = true)
    public GetUserInfoResponse findUserInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(NotFoundUserException::new);

        return GetUserInfoResponse.of(
                user.getNickname(),
                user.getBirth(),
                user.getGender().getDescription(),
                user.getJob().getDescription(),
                user.getHobbyHealingStyle(),
                user.getStressReliefStyle(),
                user.getPersonality()
        );
    }

    @Transactional
    public void processSignOut(Long kakaoId) {
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByKakaoId(kakaoId);
        refreshToken.ifPresent(refreshTokenRepository::delete);
    }

    @Transactional
    public void modifyUserBasicInfo(PatchBasicInfoRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(NotFoundUserException::new);

        user.updateBasicInfo(
                request.getNickname(),
                request.getBirth(),
                Gender.from(request.getGender()),
                Job.from(request.getJob())
        );
    }

    @Transactional
    public void modifyHobbyHealingStyle(PatchHobbyHealingStyleRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(NotFoundUserException::new);

        user.updateHobbyHealingStyle(request.getHobbyHealingStyle());
    }

    @Transactional
    public void modifyStressReliefStyle(PatchStressReliefStyleRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(NotFoundUserException::new);

        user.updateStressReliefStyle(request.getStressReliefStyle());
    }

    @Transactional
    public void modifyPersonality(PatchPersonalityRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(NotFoundUserException::new);

        user.updatePersonality(request.getPersonality());
    }

    @Transactional
    public void processReissue(PostReissueTokenRequest request, HttpServletResponse httpServletResponse) {
        String requestToken = request.getRefreshToken();

        if (requestToken.startsWith("Bearer")) {
            requestToken = requestToken.substring(7);
        }

        RefreshToken refreshToken = jwtProvider.validateRefreshToken(requestToken);
        TokenDto tokenDto = jwtProvider.createAllToken(refreshToken.getKakaoId());
        refreshToken.updateRefreshToken(tokenDto.getRefreshToken());
        jwtProvider.setResponseHeaderToken(httpServletResponse, tokenDto);
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(NotFoundUserException::new);
    }
}
