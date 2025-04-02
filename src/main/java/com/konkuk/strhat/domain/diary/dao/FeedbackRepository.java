package com.konkuk.strhat.domain.diary.dao;

import com.konkuk.strhat.domain.diary.entity.Diary;
import com.konkuk.strhat.domain.diary.entity.Feedback;
import com.konkuk.strhat.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
