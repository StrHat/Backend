package com.konkuk.strhat.domain.user.application;

import com.konkuk.strhat.domain.diary.dao.DiaryRepository;
import com.konkuk.strhat.domain.diary.dao.FeedbackRepository;
import com.konkuk.strhat.domain.diary.entity.Diary;
import com.konkuk.strhat.domain.diary.entity.Feedback;
import com.konkuk.strhat.domain.stressScore.dao.StressScoreRepository;
import com.konkuk.strhat.domain.stressScore.entity.StressScore;
import com.konkuk.strhat.domain.user.dao.UserRepository;
import com.konkuk.strhat.domain.user.dto.GetHomeResponse;
import com.konkuk.strhat.domain.user.entity.User;
import com.konkuk.strhat.domain.user.exception.NotFoundUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final UserRepository userRepository;
    private final FeedbackRepository feedbackRepository;
    private final DiaryRepository diaryRepository;
    private final StressScoreRepository stressScoreRepository;

    @Transactional(readOnly = true)
    public GetHomeResponse findHomeInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(NotFoundUserException::new);

        Optional<Diary> diary = diaryRepository.findByDiaryDateAndUser(LocalDate.now(), user);

        if (diary.isEmpty()) {
            return GetHomeResponse.from(user);
        }

        Optional<Feedback> feedback = feedbackRepository.findByDiary(diary.get());
        Optional<StressScore> stressScore = stressScoreRepository
                .findByDiaryAndUser(diary.get(), user);

        if (feedback.isEmpty() && stressScore.isEmpty()) {
            return GetHomeResponse.of(user, diary.get());
        }
        if (feedback.isEmpty()) {
            return GetHomeResponse.of(user, diary.get(), stressScore.get());
        }
        if (stressScore.isEmpty()) {
            return GetHomeResponse.of(user, diary.get(), feedback.get());
        }

        return GetHomeResponse.of(user, diary.get(), feedback.get(), stressScore.get());

    }
}
