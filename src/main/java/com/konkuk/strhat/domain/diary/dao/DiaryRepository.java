package com.konkuk.strhat.domain.diary.dao;

import com.konkuk.strhat.domain.diary.entity.Diary;
import com.konkuk.strhat.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    boolean existsByDiaryDateAndUser(LocalDate diaryDate, User user);
    Optional<Diary> findByDiaryDateAndUser(LocalDate diaryDate, User user);
    List<Diary> findAllByUserAndDiaryDateBetweenOrderByDiaryDateAsc(User user, LocalDate weekStart, LocalDate weekEnd);
}
