package com.konkuk.strhat.domain.selfDiagnosis.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum PHQ9Question {
    QUESTION_1(
            1,
            "기분이 가라앉거나, 우울하거나, 희망이 없다고 느꼈다."
    ),
    QUESTION_2(
            2,
            "평소 하던 일에 흥미가 없어지거나 즐거움을 느끼지 못했다."
    ),
    QUESTION_3(
            3,
            "잠들기가 어렵거나 자꾸 깼다. / 혹은 너무 많이 잤다."
    ),
    QUESTION_4(
            4,
            "평소보다 식욕이 줄었다. / 혹은 평소보다 많이 먹었다."
    ),
    QUESTION_5(
            5,
            "다른 사람들이 눈치 챌 정도로 평소보다 말과 행동이 느려졌다. / 혹은 너무 안절부절 못해서 가만히 앉아있을 수 없었다."
    ),
    QUESTION_6(
            6,
            "피곤하고 기운이 없었다."
    ),
    QUESTION_7(
            7,
            "내가 잘 못했거나, 실패했다는 생각이 들었다. / 혹은 자신과 가족을 실망시켰다고 생각했다."
    ),
    QUESTION_8(
            8,
            "신문을 읽거나 TV를 보는 것과 같은 일상적인 일에도 집중할 수가 없었다."
    ),
    QUESTION_9(
            9,
            "차라리 죽는 것이 더 낫겠다고 생각했다. / 혹은 자해할 생각을 했다."
    );

    private final int index;
    private final String questionText;

}
