package com.konkuk.strhat.domain.user.dto;

import com.konkuk.strhat.domain.diary.entity.Diary;
import com.konkuk.strhat.domain.diary.entity.Feedback;
import com.konkuk.strhat.domain.stressScore.entity.StressScore;
import com.konkuk.strhat.domain.stressScore.exception.ScoreOutOfRangeException;
import com.konkuk.strhat.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetHomeResponse {

    private boolean hasDiary;
    private String nickname;
    private Integer emotion;
    private String[] positiveEmotions;
    private String stressReliefSuggestion;
    private Integer stressScore;
    private String stressLevel;

    public static GetHomeResponse from(User user) {
        return GetHomeResponse.builder()
                .hasDiary(false)
                .nickname(user.getNickname())
                .build();
    }

    public static GetHomeResponse of(User user, Diary diary) {
        return GetHomeResponse.builder()
                .hasDiary(true)
                .nickname(user.getNickname())
                .emotion(diary.getEmotion())
                .build();
    }

    public static GetHomeResponse of(User user, Diary diary, Feedback feedback) {
        return GetHomeResponse.builder()
                .hasDiary(true)
                .nickname(user.getNickname())
                .emotion(diary.getEmotion())
                .positiveEmotions(feedback.getPositiveEmotionArray())
                .stressReliefSuggestion(feedback.getStressReliefSuggestion())
                .build();
    }

    public static GetHomeResponse of(User user, Diary diary, StressScore stressScore) {
        return GetHomeResponse.builder()
                .hasDiary(true)
                .nickname(user.getNickname())
                .emotion(diary.getEmotion())
                .stressScore(stressScore.getScore())
                .stressLevel(getStressLevelText(stressScore.getScore()))
                .build();
    }

    public static GetHomeResponse of(User user, Diary diary, Feedback feedback, StressScore stressScore) {
        return GetHomeResponse.builder()
                .hasDiary(true)
                .nickname(user.getNickname())
                .emotion(diary.getEmotion())
                .positiveEmotions(feedback.getPositiveEmotionArray())
                .stressReliefSuggestion(feedback.getStressReliefSuggestion())
                .stressScore(stressScore.getScore())
                .stressLevel(getStressLevelText(stressScore.getScore()))
                .build();
    }

    private static String getStressLevelText(int score) {
        if (1 <= score && score <= 5) return "낮은 스트레스 수준";
        if (6 <= score && score <= 8) return "보통 스트레스 수준";
        if (9 <= score && score <= 10) return "높은 스트레스 수준";
        throw new ScoreOutOfRangeException("1 ~ 10 사이의 정수 이외의 점수는 처리할 수 없습니다. 입력된 점수: " + score);
    }
}
