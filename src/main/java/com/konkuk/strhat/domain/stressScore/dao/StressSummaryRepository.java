package com.konkuk.strhat.domain.stressScore.dao;

import com.konkuk.strhat.domain.stressScore.entity.StressSummary;
import com.konkuk.strhat.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface StressSummaryRepository extends JpaRepository<StressSummary, Long> {
    Optional<StressSummary> findByUserAndWeekStartDate(User user, LocalDate weekStart);
    boolean existsByUserAndWeekStartDate(User user, LocalDate weekStart);

}
