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
                // 시스템 역할
                GptRequestMessage.system("""
                    당신은 심리학 기반 감정 분석과 정서적 피드백에 능숙한 심리 전문가입니다.
                    사용자 일기에서 정서적 단서를 세심하게 포착하고, 공감 어린 말투로 따뜻한 피드백을 제공해야 합니다.
                    스트레스 해소 방법은 단순한 휴식이 아니라 실질적 제안을 포함해야 합니다.
                    """),

                // 사용자 입력 메시지
                GptRequestMessage.user(String.format("""
                    [입력]
                    1. 사용자 성격 특성: %s
                    2. 일기 내용: %s
                    """, request.getUserTraits(), request.getDiaryContent())),

                // 어시스턴트 역할
                GptRequestMessage.assistant(String.format("""
                    [응답 작성 규칙]
                    1. 모든 응답은 정중하고 따뜻한 어조의 한국어로 작성해주세요.
                    2. 응답은 아래 4개 JSON 필드를 포함한 구조로 작성합니다: "%s", "%s", "%s", "%s".
                    
                    - "%s": 일기에서 감지되는 주요 긍정 감정 명사 3개를 추출하세요. 감정 단어는 사용자의 정서 상태를 설명하는 일반 명사 형태로 작성합니다. 추출된 감정이 없더라도 "없음"을 포함해 3개의 형식을 유지합니다.
                    - "%s": 일기에서 감지되는 주요 부정 감정 명사 3개를 추출하세요. 형식은 위와 동일하게 3개입니다. 추출된 감정이 없더라도 "없음"을 포함해 3개의 형식을 유지합니다.
                    - "%s": 일기를 전체적으로 요약하며 사용자의 감정에 공감해주세요. 핵심 감정 흐름을 짚어주고 이모지 1~2개로 정서적 친근감을 더해주세요.
                    - "%s": 일기 속 맥락에 맞는 실용적 스트레스 해소 방법을 하나 제안해주세요. 단순한 “쉬세요”를 지양하고, 실질적인 제안을 해주세요.

                    [예시 응답 형식]
                    {
                        "%s": ["설렘", "평온함", "성취감"],
                        "%s": ["피로", "초조", "지침"],
                        "%s": "오늘 하루 정말 수고 많으셨어요 😊 스트레스를 참아내느라 마음이 많이 고단하셨을 것 같아요.",
                        "%s": "오늘은 혼자 있는 시간을 가지면서 자신을 돌보는 시간을 가져보면 어떨까요? 조용한 공간에서 향초를 켜고 책을 읽는 것도 좋은 방법이 될 수 있어요."
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