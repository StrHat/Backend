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
                GptRequestMessage.system("You are a psychological expert providing empathetic and insightful diary feedback based on psychological principles."),
                GptRequestMessage.user(String.format("""
                    [Input]
                    1. User traits: %s
                    2. Diary entry: %s
                    """, request.getUserTraits(), request.getDiaryContent())),
                GptRequestMessage.assistant(String.format("""
                    [Response Instructions]
                    1. Respond in Korean using polite and friendly language.
                    2. Structure the response using the following fields: "%s", "%s", "%s", and "%s".
                    3. %s: Extract and list 3 key positive emotion nouns felt in the diary. (없다면 각 엔트리에 "없음"이라도 포함해서 3개의 문자열을 담은 배열 포맷을 만족해야 한다.)
                    4. %s: Extract and list 3 key negative emotion nouns felt in the diary. (없다면 각 엔트리에 "없음"이라도 포함해서 3개의 문자열을 담은 배열 포맷을 만족해야 한다.)
                    5. %s: Summarize the diary empathetically, reflecting the user's emotions. Include 1–2 emojis.
                    6. %s: Provide practical, realistic stress-relief suggestions tailored to the specific situation described in the user’s current diary entry, ensuring the advice directly addresses their present concerns.
                    Use the user’s personality traits, hobbies, and preferred stress-relief methods as context, but do not simply repeat the same advice or limit suggestions to the user’s usual coping methods. Maintain a warm and polite tone, and deliver the suggestions in Korean using 존댓말.
                    
                    [Example Response Format]
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