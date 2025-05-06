package com.konkuk.strhat.domain.diary.application;

import com.konkuk.strhat.domain.diary.dao.DiaryRepository;
import com.konkuk.strhat.domain.diary.dao.FeedbackRepository;
import com.konkuk.strhat.domain.diary.dto.*;
import com.konkuk.strhat.domain.diary.entity.Diary;
import com.konkuk.strhat.domain.diary.entity.Feedback;
import com.konkuk.strhat.domain.diary.exception.*;
import com.konkuk.strhat.domain.user.dao.UserRepository;
import com.konkuk.strhat.domain.user.entity.User;
import com.konkuk.strhat.domain.user.exception.NotFoundUserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public CheckDiaryResponse checkDiaryExists(Long currentUserId, LocalDate date){
        User user = userRepository.findById(currentUserId)
                .orElseThrow(NotFoundUserException::new);

        Optional<Diary> diary = diaryRepository.findByDiaryDateAndUser(date, user);

        CheckDiaryResponse response;
        if(diary.isPresent()){
            Integer emotion = diary.get().getEmotion();
            String content = diary.get().getContent();
            response = CheckDiaryResponse.of(true, emotion, content.substring(0, Math.min(content.length(), 70)));
        } else{
            response = CheckDiaryResponse.of(false, null, null);
        }
        return response;
    }

    @Transactional
    public Diary saveDiary(Long currentUserId, DiarySaveRequest request) {

        User user = userRepository.findById(currentUserId)
                .orElseThrow(NotFoundUserException::new);

        if(diaryRepository.existsByDiaryDateAndUser(request.getDate(), user)){
            throw new DuplicateDiaryException();
        }

        try {
            Diary diary = request.toDiaryEntity(user);
            return diaryRepository.save(diary);
        } catch (Exception e) {
            throw new DiarySaveException();
        }
    }

    @Transactional
    public DiarySaveResponse saveFeedback(Feedback feedback) {
        feedbackRepository.save(feedback);
        return DiarySaveResponse.of(
                feedback.getDiarySummary(),
                feedback.getPositiveEmotionArray(),
                feedback.getNegativeEmotionArray(),
                feedback.getStressReliefSuggestion());
    }

    @Transactional(readOnly = true)
    public DiaryContentResponse readDiary(Long currentUserId, LocalDate date) {
        User user = userRepository.findById(currentUserId)
                .orElseThrow(NotFoundUserException::new);

        Diary diary = diaryRepository.findByDiaryDateAndUser(date, user)
                .orElseThrow(NotFoundDiaryException::new);

        return DiaryContentResponse.from(diary);
    }

    @Transactional(readOnly = true)
    public FeedbackResponse readFeedback(Long currentUserId, LocalDate date) {
        User user = userRepository.findById(currentUserId)
                .orElseThrow(NotFoundUserException::new);

        Diary diary = diaryRepository.findByDiaryDateAndUser(date, user)
                .orElseThrow(NotFoundDiaryException::new);

        Feedback feedback = feedbackRepository.findByDiary(diary)
                .orElseThrow(NotFoundFeedbackException::new);

        return FeedbackResponse.from(feedback);
    }
}
