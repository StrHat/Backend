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
                GptRequestMessage.system("당신은 사용자의 정보를 기반으로 스트레스 원인을 정확히 분석해주는 심리 전문가입니다."),
                GptRequestMessage.user(String.format("""
                    [Input]
                    1. 사용자 성향 정보: %s
                    2. 오늘의 일기 내용: %s
                    3. 오늘의 대화 내역: %s
                    """, request.getUserTraits(), request.getDiaryContents(), request.getChatLog())),
                GptRequestMessage.assistant(String.format("""
                    [Response Instructions]
                    1. %s: 일주일 간의 일기 내용, 대화내역을 보고 사용자의 주간 스트레스 원인을 분석을 해주세요. 사용자 성향 정보는 참고만 해주세요. 사용자는 닉네임으로 불러주세요.
                    
                    [Example Response Format]
                    {
                        "%s": "사용자는 완벽주의적이고 내성적인 성향을 가지고 있어서 발표나 의견 충돌, 야근, 인적 손실, 신체적 피로, 그리고 가족 관련 걱정 등 다양한 요인들이 스트레스를 유발했을 것으로 판단됩니다. 이러한 스트레스 요인들이 하나씩 쌓이며 사용자의 마음과 몸에 부담을 주었을 것입니다. 스트레스 관리를 위해 업무에서 완벽을 추구하는 것보다 실수를 수용하고 동료와 의견을 잘 조율하며, 일과 휴식을 균형 있게 유지하는 것이 중요해 보입니다."
                    }
                    """,
                        SUMMARY,
                        SUMMARY
                ))
        );
    }

    public static class WeeklyStressSummaryFieldNames {
        public static final String SUMMARY = "weekly_stress_factor_summary";
    }

}
