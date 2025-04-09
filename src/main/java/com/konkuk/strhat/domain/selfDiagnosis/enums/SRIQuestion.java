package com.konkuk.strhat.domain.selfDiagnosis.enums;

public enum SRIQuestion {
    QUESTION_1("일에 실수가 많다."),
    QUESTION_2("말하기 싫다."),
    QUESTION_3("가슴이 답답하다."),
    QUESTION_4("화가 난다."),
    QUESTION_5("안절부절 못한다."),
    QUESTION_6("소화가 안된다."),
    QUESTION_7("배가 아프다."),
    QUESTION_8("소리를 지르고 싶다."),
    QUESTION_9("한숨이 나온다."),
    QUESTION_10("어지럽다."),
    QUESTION_11("만사가 귀찮다."),
    QUESTION_12("잡념이 생긴다."),
    QUESTION_13("쉽게 피로를 느낀다."),
    QUESTION_14("온몸에 힘이 빠진다."),
    QUESTION_15("자신감을 잃었다."),
    QUESTION_16("긴장된다."),
    QUESTION_17("몸이 떨린다."),
    QUESTION_18("누군가를 때리고 싶다."),
    QUESTION_19("의욕이 떨어졌다."),
    QUESTION_20("울고 싶다."),
    QUESTION_21("신경이 날카로워졌다."),
    QUESTION_22("내가 하는 일에 전망이 없다."),
    QUESTION_23("멍하게 있다."),
    QUESTION_24("누군가를 미워한다."),
    QUESTION_25("한가지 생각에 헤어나지 못한다."),
    QUESTION_26("목소리가 커졌다."),
    QUESTION_27("마음이 급해지거나 일에 쫒기는 느낌이다."),
    QUESTION_28("행동이 거칠어졌다.(난폭운전, 욕설, 몸싸움 등)"),
    QUESTION_29("무엇인가를 부수고 싶어졌다."),
    QUESTION_30("말이 없어졌다."),
    QUESTION_31("머리가 무겁거나 아프다."),
    QUESTION_32("가슴이 두근거린다."),
    QUESTION_33("누군가를 죽이고 싶다."),
    QUESTION_34("얼굴이 붉어지거나 화끈거린다."),
    QUESTION_35("지루하다."),
    QUESTION_36("참을성이 없다."),
    QUESTION_37("얼굴표정이 굳어졌다."),
    QUESTION_38("나는 아무 쓸모가 없는 사람이다."),
    QUESTION_39("움직이기 싫다");


    private final String questionText;
    SRIQuestion(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionText() {
        return questionText;
    }
}
