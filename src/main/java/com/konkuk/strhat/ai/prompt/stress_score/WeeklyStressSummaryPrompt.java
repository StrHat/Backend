package com.konkuk.strhat.ai.prompt.stress_score;

import com.konkuk.strhat.ai.dto.GptRequestMessage;
import com.konkuk.strhat.ai.prompt.GptPrompt;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.konkuk.strhat.ai.prompt.stress_score.WeeklyStressSummaryPrompt.WeeklyStressSummaryFieldNames.*;

@RequiredArgsConstructor
public class WeeklyStressSummaryPrompt implements GptPrompt {

    private final WeeklyStressSummaryRequestDto request;

    @Override
    public List<GptRequestMessage> toMessages() {
        return List.of(
                // 시스템 역할
                GptRequestMessage.system("""
                    당신은 임상심리학 및 스트레스 관리에 전문성을 가진 심리 전문가입니다.
                    사용자의 주간 텍스트 데이터를 분석해 스트레스 요인을 과학적 근거에 기반하여 분석하고, 누구나 이해하기 쉽게 설명해주세요.
                """),

                // 사용자 입력 메시지
                GptRequestMessage.user(String.format("""
                    [입력]
                    1. 사용자 성향 정보: %s
                    2. 오늘의 일기 내용: %s
                    3. 오늘의 대화 내역: %s
                    """, request.getUserTraits(), request.getDiaryContents(), request.getChatLog())),

                // 어시스턴트 역할
                GptRequestMessage.assistant(String.format("""
                    [응답 작성 규칙]
                    - 목표: 아래의 내용을 기반으로 사용자(%s)의 주간 스트레스 원인을 다각도로 분석해주세요.
                    - 사용자 성향 정보는 스트레스 반응의 민감도나 유형을 파악하는 데 참고자료로만 활용하세요.
                    - 일기 및 대화 내역은 스트레스 유발 사건, 반복되는 감정 패턴, 표현된 행동 변화의 핵심 분석 대상입니다.
                    - 단순 요약이 아닌 스트레스 심리 이론(ex: 인지 평가 이론, 회피-대처 모델, 직무 스트레스 모델)에 근거하여 해석해주세요.
                    - 이 응답을 받는 사용자는 심리학 지식이 전혀 없는 일반인이라는 점을 고려해서 답해주세요.
                    - 분석 후에는 정서적 지지 또는 행동 변화에 도움이 될 수 있는 간단한 제언(1~2줄)을 포함해주세요.
        
                    [예시 응답 형식]
                    {
                        "%s": "%s님은 주어진 일을 잘 해내고 싶은 책임감이 크기 때문에 이번 주에는 '지속된 업무 압박'과 '의사소통 오해'로 인해 감정적으로 많이 소모된 것 같아요. 일기를 쓸 때 매일 작은 성취라도 같이 기록해보는 건 어때요?"
                    }
                    """,
                        SUMMARY,
                        SUMMARY,
                        request.getNickname()
                ))
        );
    }

    public static class WeeklyStressSummaryFieldNames {
        public static final String SUMMARY = "weekly_stress_factor_summary";
    }

}
