package com.konkuk.strhat.domain.selfDiagnosis.dao;

import com.konkuk.strhat.domain.selfDiagnosis.entity.SelfDiagnosis;
import com.konkuk.strhat.domain.selfDiagnosis.enums.SelfDiagnosisType;
import com.konkuk.strhat.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface SelfDiagnosisRepository extends JpaRepository<SelfDiagnosis, Long> {

    Optional<SelfDiagnosis> findBySelfDiagnosisDateAndUserAndType(LocalDate date, User user, SelfDiagnosisType type);
}
