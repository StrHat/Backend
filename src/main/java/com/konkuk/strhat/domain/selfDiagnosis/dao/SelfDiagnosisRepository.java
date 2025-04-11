package com.konkuk.strhat.domain.selfDiagnosis.dao;

import com.konkuk.strhat.domain.selfDiagnosis.entity.SelfDiagnosis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SelfDiagnosisRepository extends JpaRepository<SelfDiagnosis, Long> {
}
