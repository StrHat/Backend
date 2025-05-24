package com.konkuk.strhat.domain.stressScore.dao;

import com.konkuk.strhat.domain.stressScore.entity.StressScore;
import com.konkuk.strhat.domain.user.entity.User;
import com.konkuk.strhat.domain.diary.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StressScoreRepository extends JpaRepository<StressScore, Long> {
    @Query("""
        SELECT ss 
        FROM StressScore ss, Diary d
        WHERE d.stressScore = ss
        AND d = :diary
        AND d.user = :user
    """)
    Optional<StressScore> findByDiaryAndUser(
            @Param("diary") Diary diary,
            @Param("user") User user
    );
}
