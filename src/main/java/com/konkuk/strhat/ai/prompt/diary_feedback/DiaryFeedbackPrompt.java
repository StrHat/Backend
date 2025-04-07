package com.konkuk.strhat.ai.prompt.diary_feedback;

import com.konkuk.strhat.ai.dto.GptRequestMessage;
import com.konkuk.strhat.ai.prompt.GptPrompt;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.konkuk.strhat.ai.prompt.diary_feedback.DiaryFeedbackPrompt.DiaryFeedbackFieldNames.POSITIVE;
import static com.konkuk.strhat.ai.prompt.diary_feedback.DiaryFeedbackPrompt.DiaryFeedbackFieldNames.NEGATIVE;
import static com.konkuk.strhat.ai.prompt.diary_feedback.DiaryFeedbackPrompt.DiaryFeedbackFieldNames.SUMMARY;
import static com.konkuk.strhat.ai.prompt.diary_feedback.DiaryFeedbackPrompt.DiaryFeedbackFieldNames.SUGGESTION;

@RequiredArgsConstructor
public class DiaryFeedbackPrompt implements GptPrompt {

    private final DiaryFeedbackRequestDto request;

    @Override
    public List<GptRequestMessage> toMessages() {
        return List.of(
                GptRequestMessage.system("당신은 심리학적 지식을 가지고 일기 피드백을 제공하는 심리 전문가입니다."),
                GptRequestMessage.user(String.format("""
                    [요청]
                    1. 사용자의 정보: %s
                    2. 사용자의 일기 내용: %s
                    """, request.getUserTraits(), request.getDiaryContent())),
                GptRequestMessage.assistant(String.format("""
                    [응답 프롬프트]
                    1. 문장은 한국어 및 친근한 존댓말 적는다.
                    2. 응답은 "%s", "%s", "%s", "%s" 으로 나누어 보낸다.
                    3. %s: 일기에서 느껴지는 주요 긍정 감정 명사를 3개 선정한다.
                    4. %s: 일기에서 느껴지는 주요 부정 감정 명사를 3개 선정한다.
                    5. %s: 사용자에게 공감하는 형식으로 일기를 요약 및 공감한다. 이모지를 1개~2개 포함한다.
                    6. %s: 사용자의 정보를 반영하여 맞춤형 스트레스 해소 방법을 제안한다.

                    [예시 응답, 형식 참고용]
                    {
                        "%s": ["성취감", "안도감", "즐거움"],
                        "%s": ["불안", "긴장", "피로감"],
                        "%s": "오늘은 알바 때문에 마음이 복잡하고 힘드셨던 하루였던 것 같아요😢 ...",
                        "%s": "따뜻한 이불 속에서 잔잔한 음악을 들으며 ... 하는 게 어떨까요?"
                    }
                    """,
                        POSITIVE, NEGATIVE, SUMMARY, SUGGESTION,
                        POSITIVE, NEGATIVE, SUMMARY, SUGGESTION,
                        POSITIVE, NEGATIVE, SUMMARY, SUGGESTION
                ))
        );
    }

    public static class DiaryFeedbackFieldNames {
        public static final String POSITIVE = "positive_emotion_keywords";
        public static final String NEGATIVE = "negative_emotion_keywords";
        public static final String SUMMARY = "diary_summary";
        public static final String SUGGESTION = "stress_relief_suggestions";
    }

}