package com.konkuk.strhat.domain.stressScore.dao;

import com.konkuk.strhat.domain.stressScore.entity.StressScore;
import com.konkuk.strhat.domain.user.entity.User;
import com.konkuk.strhat.domain.diary.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StressScoreRepository extends JpaRepository<StressScore, Long> {
}
