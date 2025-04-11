package com.konkuk.strhat.domain.selfDiagnosis.application;

import com.konkuk.strhat.domain.selfDiagnosis.dto.SelfDiagnosisQuestion;
import com.konkuk.strhat.domain.selfDiagnosis.enums.PHQ9Question;
import com.konkuk.strhat.domain.selfDiagnosis.enums.PSSQuestion;
import com.konkuk.strhat.domain.selfDiagnosis.enums.SRIQuestion;
import com.konkuk.strhat.domain.selfDiagnosis.exception.UnsupportedSelfDiagnosisTypeException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SelfDiagnosisService {

    public List<SelfDiagnosisQuestion> findSelfDiagnosis(String type) {
        if (type.equals("pss")) {
            return pssQuestionList();
        }
        if (type.equals("sri")) {
            return sriQuestionList();
        }
        if (type.equals("phq-9")) {
            return phq9QuestionList();
        }

        throw new UnsupportedSelfDiagnosisTypeException();
    }

    private List<SelfDiagnosisQuestion> pssQuestionList() {
        return Arrays.stream(PSSQuestion.values())
                .map(question -> SelfDiagnosisQuestion.of(
                        question.getIndex(),
                        question.getQuestionText()))
                .collect(Collectors.toList());
    }

    private List<SelfDiagnosisQuestion> sriQuestionList() {
        return Arrays.stream(SRIQuestion.values())
                .map(question -> SelfDiagnosisQuestion.of(
                        question.getIndex(),
                        question.getQuestionText()))
                .collect(Collectors.toList());
    }

    private List<SelfDiagnosisQuestion> phq9QuestionList() {
        return Arrays.stream(PHQ9Question.values())
                .map(question -> SelfDiagnosisQuestion.of(
                        question.getIndex(),
                        question.getQuestionText()))
                .collect(Collectors.toList());
    }
}
