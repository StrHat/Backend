package com.konkuk.strhat.domain.selfDiagnosis.application;

import com.konkuk.strhat.domain.selfDiagnosis.dao.SelfDiagnosisRepository;
import com.konkuk.strhat.domain.selfDiagnosis.dto.PostSelfDiagnosisRequest;
import com.konkuk.strhat.domain.selfDiagnosis.dto.SelfDiagnosisQuestion;
import com.konkuk.strhat.domain.selfDiagnosis.entity.SelfDiagnosis;
import com.konkuk.strhat.domain.selfDiagnosis.enums.PHQ9Question;
import com.konkuk.strhat.domain.selfDiagnosis.enums.PSSQuestion;
import com.konkuk.strhat.domain.selfDiagnosis.enums.SRIQuestion;
import com.konkuk.strhat.domain.selfDiagnosis.enums.SelfDiagnosisType;
import com.konkuk.strhat.domain.selfDiagnosis.exception.UnsupportedSelfDiagnosisTypeException;
import com.konkuk.strhat.domain.user.dao.UserRepository;
import com.konkuk.strhat.domain.user.entity.User;
import com.konkuk.strhat.domain.user.exception.NotFoundUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SelfDiagnosisService {

    private final UserRepository userRepository;
    private final SelfDiagnosisRepository selfDiagnosisRepository;

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

    @Transactional
    public void generateSelfDiagnosisResult(PostSelfDiagnosisRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(NotFoundUserException::new);

        SelfDiagnosis selfDiagnosis = SelfDiagnosis.builder()
                .type(SelfDiagnosisType.toSelfDiagnosisType(request.getType()))
                .score(request.getSelfDiagnosisScore())
                .build();

        user.addSelfDiagnosis(selfDiagnosis);
        selfDiagnosisRepository.save(selfDiagnosis);
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
