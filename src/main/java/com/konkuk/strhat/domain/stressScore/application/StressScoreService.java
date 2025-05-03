package com.konkuk.strhat.domain.stressScore.application;

import com.konkuk.strhat.domain.diary.entity.Diary;
import com.konkuk.strhat.domain.diary.exception.NotFoundDiaryException;
import com.konkuk.strhat.domain.stressScore.dao.StressScoreRepository;
import com.konkuk.strhat.domain.stressScore.dto.DailyStressScoreResponse;
import com.konkuk.strhat.domain.stressScore.entity.StressScore;
import com.konkuk.strhat.domain.stressScore.exception.DuplicateStressScoreException;
import com.konkuk.strhat.domain.user.dao.UserRepository;
import com.konkuk.strhat.domain.user.entity.User;
import com.konkuk.strhat.domain.user.exception.NotFoundUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


@Service
@RequiredArgsConstructor
public class StressScoreService {
    private final StressScoreRepository stressScoreRepository;
    private final UserRepository userRepository;

    @Transactional
    public DailyStressScoreResponse getOrCreateStressScore(LocalDate date, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(NotFoundUserException::new);

        StressScore score = stressScoreRepository.findByDiaryUserAndStressScoreDate(user, date)
                .orElseGet(() -> generateStressScore(user, date));

        return DailyStressScoreResponse.from(user.getNickname(), score);
    }

    private StressScore generateStressScore(User user, LocalDate date) { // TODO AI 사용하는 로직으로 수정 필요
        Diary diary = user.getDiaries().stream()
                .filter(d -> d.getDiaryDate().equals(date))
                .findFirst()
                .orElseThrow(NotFoundDiaryException::new);

        // TODO 채팅 내역도 반영하는 로직 추가 필요

        StressScore mock = StressScore.builder()
                .score(6)
                .stressFactor("스트레스 요인 임시 데이터")
                .diary(diary)
                .build();

        if (stressScoreRepository.existsByDiary(diary)) {
            throw new DuplicateStressScoreException();
        }

        return stressScoreRepository.save(mock);
    }
}
