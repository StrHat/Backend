package com.konkuk.strhat.domain.user.application;

import com.konkuk.strhat.domain.user.dao.RefreshTokenRepository;
import com.konkuk.strhat.domain.user.dao.UserRepository;
import com.konkuk.strhat.domain.user.dto.GetUserInfoResponse;
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
}
