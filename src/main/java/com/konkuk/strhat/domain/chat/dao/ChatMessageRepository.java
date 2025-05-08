package com.konkuk.strhat.domain.chat.dao;

import com.konkuk.strhat.domain.chat.entity.ChatMessage;
import com.konkuk.strhat.domain.diary.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findAllByDiary(Diary diary);
}
