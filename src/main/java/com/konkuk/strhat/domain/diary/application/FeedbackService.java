package com.konkuk.strhat.domain.diary.application;

import com.konkuk.strhat.domain.diary.dao.FeedbackRepository;
import com.konkuk.strhat.domain.diary.entity.Diary;
import com.konkuk.strhat.domain.diary.entity.Feedback;
import com.konkuk.strhat.domain.diary.exception.FeedbackGenerateException;
import com.konkuk.strhat.domain.diary.exception.FeedbackSaveException;
import com.konkuk.strhat.domain.diary.exception.InvalidFeedbackEmotionFormatException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;

    @Transactional
    public Feedback generateFeedbackAndSave(Diary diary) {

        Feedback feedback;

        // AI 피드백 생성
        try {
            // 임시
            feedback = Feedback.builder()
                    .diarySummary("오늘 하루를 잘 정리했어요.")
                    .positiveEmotions("기쁨, 감사, 행복")
                    .negativeEmotions("피로, 짜증, 슬픔")
                    .stressReliefSuggestion("산책을 추천해요.")
                    .diary(diary)
                    .build();
        }catch (Exception e) {
            throw new FeedbackGenerateException(e.getMessage());
        }

        // 감정 키워드 개수 검증
        if (feedback.getPositiveEmotionArray().length != 3 || feedback.getNegativeEmotionArray().length != 3) {
            throw new InvalidFeedbackEmotionFormatException(feedback.getPositiveEmotions(), feedback.getNegativeEmotions());
        }

        // AI 피드백 데이터 저장
        try {
            feedbackRepository.save(feedback);
        } catch (Exception e) {
            throw new FeedbackSaveException();
        }

        return feedback;
    }
}
