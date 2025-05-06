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
                GptRequestMessage.system("You are a mental health assistant trained to assess diary entries."),
                GptRequestMessage.user(String.format("""
                    [Input]
                    1. 사용자 성향 정보: %s
                    1. 오늘의 일기 내용: %s
                    2. 오늘의 대화 내역: %s
                    """, request.getUserTraits(), request.getDiaryContent(), request.getChatLog())),
                GptRequestMessage.assistant(String.format("""
                    [Response Instructions]
                    1. %s: 사용자의 일기와 대화내역을 기반으로 스트레스 점수를 1부터 10까지 숫자 하나로 추론해주세요.
                       1~5까지는 낮은 스트레스, 6~8까지는 보통 스트레스, 9~10까지는 높은 스트레스에요.
                    2. %s: 오늘의 일기 내용, 대화내역을 보고 사용자의 스트레스 원인을 분석을 해주세요. 사용자 성향 정보는 참고만 해주세요. 사용자는 닉네임으로 불러주세요.
                    
                    [Example Response Format]
                    {
                        "%s": 4,
                        "%s": "사용자는 다양한 취향을 가진 다양한 활동을 즐기며 삶을 즐기는 편인데, 시험 기간에는 공부 부담과 시간 부족으로 인한 스트레스를 많이 받는 것으로 보입니다. 여러 전공 과목을 동시에 공부해야 하는 상황에서 과연 배워야 할 것들이 끝이 없다는 생각이 불안을 유발하며, 이로 인해 조급함과 지쳐감을 느끼고 있는 모습입니다. 이외에도 자신이 즐기는 음악 청취나 외향적인 성향의 활동을 쉽게 할 수 없다는 점이 스트레스를 느끼는데 영향을 줄 수 있습니다.",
                    }
                    """,
                        SCORE, FACTOR,
                        SCORE, FACTOR
                ))
        );
    }

    public static class DailyStressScoreFieldNames {
        public static final String SCORE = "stress_score";
        public static final String FACTOR = "stress_factor";
    }

}
