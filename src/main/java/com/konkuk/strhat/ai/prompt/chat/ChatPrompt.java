package com.konkuk.strhat.ai.prompt.chat;

import com.konkuk.strhat.ai.dto.GptRequestMessage;
import com.konkuk.strhat.ai.prompt.GptPrompt;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.konkuk.strhat.ai.prompt.chat.ChatPrompt.ChatResponseFieldNames.RESPONSE;

@RequiredArgsConstructor
public class ChatPrompt implements GptPrompt {

    private final ChatRequestDto request;

    @Override
    public List<GptRequestMessage> toMessages() {
        return List.of(
                GptRequestMessage.system(String.format("""
                    당신은 사용자와 대화하며 사용자의 마음을 돌봐주는 %s 챗봇입니다.
                    """, request.getChatMode())),
                GptRequestMessage.user(String.format("""
                    [Input]
                    1. 사용자 성향 정보: %s
                    2. 오늘의 일기 내용: %s
                    3. 오늘의 이전 대화 내역: %s
                    4. 너가 지금 답해야하는 지금 사용자의 말: %s
                    """, request.getUserTraits(), request.getDiaryContent(), request.getChatLog(), request.getUserChatMessage())),
                GptRequestMessage.assistant(String.format("""
                    [응답 프롬프트]
                     1. 문장은 한국어 및 친근한 반말로 작성한다.
                     2. 응답은 반드시 1문장으로만 작성한다.
                     3. 응답은 아래 JSON 형식을 정확히 따라야 한다:
                     {
                         "%s": "답변 1문장"
                     }
                     4. 대화내역을 보고 중복된 말은 피한다. '지금 사용자의 말'에 답하며 자연스럽게 사람처럼 대화한다.
                     5. 사용자가 오늘 있었던 일 중에 긍정 요소를 찾을 수 있도록 질문을 던지는 것도 필요하다.
                     6. 모드별 답변 스타일:
                        - 공감 모드: 해결책보다는 사용자의 감정에 진심으로 '이해,위로,공감,응원,격려'하는 짧은 답변.
                        - 해결책 모드: 간단한 '공감'과 함께 구체적인 '해결책'을 제시.
                     [공감 모드 예시 응답, 참고용]
                     {
                         "%s": "친한 친구랑 싸운 상황이면 누구라도 충분히 속상할 것 같아."
                     }
                     [해결책 모드 예시 응답, 참고용]
                     {
                         "%s": "우선 친구한테 솔직하게 먼저 이야기를 꺼내보는 건 어때?"
                     }
                    """,
                        RESPONSE,
                        RESPONSE,
                        RESPONSE
                ))
        );
    }

    public static class ChatResponseFieldNames {
        public static final String RESPONSE = "chat_response";
    }

}