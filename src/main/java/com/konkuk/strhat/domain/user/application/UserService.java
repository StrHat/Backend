package com.konkuk.strhat.domain.user.application;

import com.konkuk.strhat.domain.user.dao.RefreshTokenRepository;
import com.konkuk.strhat.domain.user.dao.UserRepository;
import com.konkuk.strhat.domain.user.dto.GetUserInfoResponse;
import com.konkuk.strhat.domain.user.dto.PatchBasicInfoRequest;
import com.konkuk.strhat.domain.user.dto.PatchHobbyHealingStyleRequest;
import com.konkuk.strhat.domain.user.dto.PatchPersonalityRequest;
import com.konkuk.strhat.domain.user.dto.PatchStressReliefStyleRequest;
import com.konkuk.strhat.domain.user.dto.PostSignUpRequest;
import com.konkuk.strhat.domain.user.dto.TokenDto;
import com.konkuk.strhat.domain.user.entity.RefreshToken;
import com.konkuk.strhat.domain.user.entity.User;
import com.konkuk.strhat.domain.user.enums.Gender;
import com.konkuk.strhat.domain.user.enums.Job;
import com.konkuk.strhat.domain.user.exception.DuplicateEmailException;
import com.konkuk.strhat.domain.user.exception.NotFoundUserException;
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
    public TokenDto createUser(PostSignUpRequest request) {
        Optional<User> duplicateUser = userRepository.findByEmail(request.getEmail());

        if (duplicateUser.isPresent()) {
            throw new DuplicateEmailException();
        }

        User user = new User(request.getEmail(),
                request.getNickname(),
                request.getBirth(),
                Gender.toGender(request.getGender()),
                Job.toJob(request.getJob()),
                request.getHobbyHealingStyle(),
                request.getHobbyHealingStyle(),
                request.getPersonality());

        userRepository.save(user);
        TokenDto tokenDto = jwtProvider.createAllToken(request.getEmail());
        RefreshToken refreshToken = new RefreshToken(tokenDto.getRefreshToken(), request.getEmail());
        refreshTokenRepository.save(refreshToken);
        return tokenDto;
    }

    @Transactional(readOnly = true)
    public GetUserInfoResponse findUserInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(NotFoundUserException::new);

        return new GetUserInfoResponse(
                user.getNickname(),
                user.getBirth(),
                user.getGender().toString(),
                user.getJob().toString(),
                user.getHobbyHealingStyle(),
                user.getStressReliefStyle(),
                user.getPersonality()
        );
    }

    @Transactional
    public void processSignOut(String email) {
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByEmail(email);
        refreshToken.ifPresent(refreshTokenRepository::deleteAll);
    }

    @Transactional
    public void modifyUserBasicInfo(PatchBasicInfoRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(NotFoundUserException::new);

        user.updateBasicInfo(
                request.getNickname(),
                request.getBirth(),
                Gender.toGender(request.getGender()),
                Job.toJob(request.getJob())
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
}
