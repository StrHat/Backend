package com.konkuk.strhat.domain.self_diagnosis.enums;

public enum PSSQuestion {

    QUESTION_1(
            "지난 한 달 동안, 예상치 못한 일이 생겨서 기분 나빠진 적이 얼마나 있었나요?"
    ),
    QUESTION_2(
            "지난 한 달 동안, 중요한 일들을 통제할 수 없다고 느낀 적은 얼마나 있었나요?"
    ),
    QUESTION_3(
            "지난 한 달 동안, 초조하거나 스트레스가 쌓인다고 느낀 적은 얼마나 있었나요?"
    ),
    QUESTION_4(
            "지난 한 달 동안, 짜증나고 성가신 일들을 성공적으로 처리한 적이 얼마나 있었나요?"
    ),
    QUESTION_5(
            "지난 한 달 동안, 생활 속에서 일어난 중요한 변화들을 효과적으로 대처한 적이 얼마나 있었나요?"
    ),
    QUESTION_6(
            "지난 한 달 동안, 개인적인 문제를 처리하는 능력에 대해 자신감을 느낀 적은 얼마나 있었나요?"
    ),
    QUESTION_7(
            "지난 한 달 동안, 자신의 뜻대로 일이 진행된다고 느낀 적은 얼마나 있었나요?"
    ),
    QUESTION_8(
            "지난 한 달 동안, 매사를 잘 컨트롤하고 있다고 느낀 적이 얼마나 있었나요?"
    ),
    QUESTION_9(
            "지난 한 달 동안, 당신이 통제할 수 없는 범위에서 발생한 일 때문에 화가 난 적이 얼마나 있었나요?"
    ),
    QUESTION_10(
            "지난 한 달 동안, 어려운 일이 너무 많이 쌓여서 극복할 수 없다고 느낀 적이 얼마나 있었나요?"
    );

    private final String questionText;

    PSSQuestion(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionText() {
        return questionText;
    }
}
