package com.konkuk.strhat.ai.prompt.stress_score;

import com.konkuk.strhat.ai.dto.GptRequestMessage;
import com.konkuk.strhat.ai.prompt.GptPrompt;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.konkuk.strhat.ai.prompt.stress_score.DailyStressScorePrompt.DailyStressScoreFieldNames.*;

@RequiredArgsConstructor
public class DailyStressScorePrompt implements GptPrompt {

    private final DailyStressScoreRequestDto request;

    @Override
    public List<GptRequestMessage> toMessages() {
        return List.of(
                // 시스템 역할
                GptRequestMessage.system("""
                    당신은 스트레스 평가 이론(Lazarus & Folkman, 1984)에 기반해 일상 속 스트레스 수준을 해석하는 심리 전문가입니다.
                    사용자의 일기와 대화에서 정서적 단서들을 분석해 스트레스 수준을 정량화하고, 구체적인 원인을 누구나 이해하기 쉽게 설명해주세요.
                    """),

                // 사용자 입력 메시지
                GptRequestMessage.user(String.format("""
                    [입력]
                    1. 사용자 성향 정보: %s
                    2. 오늘의 일기 내용: %s
                    3. 오늘의 대화 내역: %s
                    """, request.getUserTraits(), request.getDiaryContent(), request.getChatLog())),

                // 어시스턴트 역할
                GptRequestMessage.assistant(String.format("""
                    [응답 지침]
                    1. %s: 사용자의 일기와 대화 내역에 나타난 스트레스 관련 언어적 지표(부정 정서, 부정 표현 등)를 기반으로 스트레스 점수를 1~10 사이에서 추론하세요.
                       - 1~5: 낮은 스트레스 (정서적으로 안정됨, 통제감 있음)
                       - 6~8: 중간 스트레스 (일시적 부담, 혼합 감정 상태)
                       - 9~10: 높은 스트레스 (지속적 불안, 무력감, 좌절감)
        
                    2. %s: 사용자의 스트레스 원인을 인지적 평가 관점에서 분석하세요.
                       - 일기 및 대화 내용에서 확인되는 스트레스 유발 요인을 중심으로 분석하고,
                       - 성향 정보는 해석의 보조 자료로만 참고하세요.
                       - 사용자 성향에 맞는 표현 방식과 조언 방식을 스스로 판단해 적용하세요.
                            - 예를 들어, 책임감이 강하거나 완벽주의 성향이 강한 경우에는 단순한 휴식보다는 우선순위 설정, 부분 완성 허용, 작은 목표 설정 등의 구체적인 제안을 해주세요.
                            - 감정을 잘 억누르거나 표현하지 않는 성향이라면 감정을 인식하고 표현하는 방향으로 실질적이고 현실적인 조언을 덧붙여주세요.
                            - 소극적이고 회피적인 성향일 경우엔 심리적 안전감을 주며 작은 행동 변화부터 시작할 수 있는 방향으로 실질적이고 현실적인 조언을 덧붙여주세요.
                       - 이 응답을 받는 사용자는 심리학 지식이 전혀 없는 일반인이라는 점을 고려해서 답해주세요.
        
                    [예시 응답 형식]
                    {
                       %s: 8,
                       %s: "지금 %s님은 여러 가지를 한꺼번에 해내야 한다는 부담감에 꽤 지쳐 있는 모습이에요. 일기에서 반복적으로 ‘시간이 모자라’거나 ‘계속 신경 써야 할 게 많다’는 표현이 보여요. 이런 상황에서는 작은 일도 더 크게 느껴질 수 있어요. 완벽하지 않아도 괜찮으니까, 오늘만큼은 가장 중요한 일 몇 가지만 정리해 먼저 처리해보는 건 어때요?"
                     }
                    """,
                        SCORE, FACTOR,
                        SCORE, FACTOR,
                        request.getNickname()
                ))
        );
    }


    public static class DailyStressScoreFieldNames {
        public static final String SCORE = "stress_score";
        public static final String FACTOR = "stress_factor";
    }

}
