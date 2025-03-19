package com.konkuk.strhat.domain.user.application;

import com.konkuk.strhat.domain.user.dao.UserRepository;
import com.konkuk.strhat.domain.user.dto.PostSignUpRequest;
import com.konkuk.strhat.domain.user.entity.User;
import com.konkuk.strhat.domain.user.enums.Gender;
import com.konkuk.strhat.domain.user.enums.Job;
import com.konkuk.strhat.domain.user.exception.DuplicateEmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void createUser(PostSignUpRequest request) {
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
    }
}
