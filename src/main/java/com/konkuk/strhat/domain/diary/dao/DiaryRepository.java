package com.konkuk.strhat.domain.diary.dao;

import com.konkuk.strhat.domain.diary.entity.Diary;
import com.konkuk.strhat.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    Optional<Diary> findByDiaryDateAndUser(LocalDate diaryDate, User user);
}
