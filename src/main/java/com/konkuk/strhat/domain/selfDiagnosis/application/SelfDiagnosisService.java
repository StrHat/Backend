package com.konkuk.strhat.domain.selfDiagnosis.application;

import com.konkuk.strhat.domain.selfDiagnosis.dao.SelfDiagnosisRepository;
import com.konkuk.strhat.domain.selfDiagnosis.dto.GetSelfDiagnosisResultResponse;
import com.konkuk.strhat.domain.selfDiagnosis.dto.PostSelfDiagnosisRequest;
import com.konkuk.strhat.domain.selfDiagnosis.dto.GetSelfDiagnosisQuestion;
import com.konkuk.strhat.domain.selfDiagnosis.entity.SelfDiagnosis;
import com.konkuk.strhat.domain.selfDiagnosis.enums.DepressionLevel;
import com.konkuk.strhat.domain.selfDiagnosis.enums.PHQ9Question;
import com.konkuk.strhat.domain.selfDiagnosis.enums.PSSQuestion;
import com.konkuk.strhat.domain.selfDiagnosis.enums.SRIQuestion;
import com.konkuk.strhat.domain.selfDiagnosis.enums.SelfDiagnosisType;
import com.konkuk.strhat.domain.selfDiagnosis.enums.StressLevel;
import com.konkuk.strhat.domain.selfDiagnosis.exception.DuplicateSelfDiagnosisException;
import com.konkuk.strhat.domain.selfDiagnosis.exception.NotFoundSelfDiagnosisException;
import com.konkuk.strhat.domain.selfDiagnosis.exception.ScoreOutOfRangeException;
import com.konkuk.strhat.domain.selfDiagnosis.exception.UnsupportedSelfDiagnosisTypeException;
import com.konkuk.strhat.domain.user.dao.UserRepository;
import com.konkuk.strhat.domain.user.entity.User;
import com.konkuk.strhat.domain.user.exception.NotFoundUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SelfDiagnosisService {

    private final UserRepository userRepository;
    private final SelfDiagnosisRepository selfDiagnosisRepository;

    public List<GetSelfDiagnosisQuestion> findSelfDiagnosis(String type) {
        return switch (type) {
            case "pss", "PSS"   -> pssQuestionList();
            case "sri", "SRI"   -> sriQuestionList();
            case "phq9", "PHQ9" -> phq9QuestionList();
            default      -> throw new UnsupportedSelfDiagnosisTypeException();
        };
    }

    @Transactional
    public void generateSelfDiagnosisResult(PostSelfDiagnosisRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(NotFoundUserException::new);

        Optional<SelfDiagnosis> optionalSelfDiagnosis =
                selfDiagnosisRepository.findBySelfDiagnosisDateAndUserAndType(LocalDate.now(), user,
                        SelfDiagnosisType.toSelfDiagnosisType(request.getType()));

        if (optionalSelfDiagnosis.isPresent()) {
            throw new DuplicateSelfDiagnosisException();
        }

        SelfDiagnosis selfDiagnosis = SelfDiagnosis.builder()
                .type(SelfDiagnosisType.toSelfDiagnosisType(request.getType()))
                .score(request.getSelfDiagnosisScore())
                .build();

        user.addSelfDiagnosis(selfDiagnosis);
        selfDiagnosisRepository.save(selfDiagnosis);
    }

    @Transactional(readOnly = true)
    public GetSelfDiagnosisResultResponse findSelfDiagnosisResult(LocalDate date, Long userId, String type) {
        User user = userRepository.findById(userId)
                .orElseThrow(NotFoundUserException::new);

        SelfDiagnosis selfDiagnosis = selfDiagnosisRepository
                .findBySelfDiagnosisDateAndUserAndType(date, user, SelfDiagnosisType.toSelfDiagnosisType(type))
                .orElseThrow(NotFoundSelfDiagnosisException::new);

        String selfDiagnosisLevel = switch (selfDiagnosis.getType()) {
            case PSS  -> getStressLevelForPSS(selfDiagnosis.getScore());
            case SRI  -> getStressLevelForSRI(selfDiagnosis.getScore());
            case PHQ9 -> getDepressionLevelForPHQ9(selfDiagnosis.getScore());
        };

        return GetSelfDiagnosisResultResponse.of(user, selfDiagnosis, selfDiagnosisLevel);
    }

    private List<GetSelfDiagnosisQuestion> pssQuestionList() {
        return Arrays.stream(PSSQuestion.values())
                .map(question -> GetSelfDiagnosisQuestion.of(
                        question.getIndex(),
                        question.getQuestionText()))
                .collect(Collectors.toList());
    }

    private List<GetSelfDiagnosisQuestion> sriQuestionList() {
        return Arrays.stream(SRIQuestion.values())
                .map(question -> GetSelfDiagnosisQuestion.of(
                        question.getIndex(),
                        question.getQuestionText()))
                .collect(Collectors.toList());
    }

    private List<GetSelfDiagnosisQuestion> phq9QuestionList() {
        return Arrays.stream(PHQ9Question.values())
                .map(question -> GetSelfDiagnosisQuestion.of(
                        question.getIndex(),
                        question.getQuestionText()))
                .collect(Collectors.toList());
    }

    private String getStressLevelForPSS(int score) {
        if (score >= 0 && score <= 13) {
            return StressLevel.NORMAL.getDescription();
        }
        if (score >= 14 && score <= 16) {
            return StressLevel.MILD.getDescription();
        }
        if (score >= 17 && score <= 18) {
            return StressLevel.MODERATE.getDescription();
        }
        if (score >= 19 && score <= 40) {
            return StressLevel.HIGH.getDescription();
        }
        throw new ScoreOutOfRangeException();
    }

    private String getStressLevelForSRI(int score) {
        if (score >= 0 && score <= 50) {
            return StressLevel.NORMAL.getDescription();
        }
        if (score >= 51 && score <= 80) {
            return StressLevel.MILD.getDescription();
        }
        if (score >= 81 && score <= 119) {
            return StressLevel.MODERATE.getDescription();
        }
        if (score >= 120 && score <= 195) {
            return StressLevel.HIGH.getDescription();
        }
        throw new ScoreOutOfRangeException();
    }

    private String getDepressionLevelForPHQ9(int score) {
        if (score >= 0 && score <= 4) {
            return DepressionLevel.MINIMAL.getDescription();
        }
        if (score >= 5 && score <= 9) {
            return DepressionLevel.MILD.getDescription();
        }
        if (score >= 10 && score <= 14) {
            return DepressionLevel.MODERATE.getDescription();
        }
        if (score >= 15 && score <= 19) {
            return DepressionLevel.ABOVE_MODERATE.getDescription();
        }
        if (score >= 20 && score <= 27) {
            return DepressionLevel.SEVERE.getDescription();
        }
        throw new ScoreOutOfRangeException();
    }
}
