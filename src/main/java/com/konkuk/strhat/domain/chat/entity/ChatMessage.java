package com.konkuk.strhat.domain.chat.entity;

import com.konkuk.strhat.domain.chat.enums.ChatMode;
import com.konkuk.strhat.domain.chat.enums.Sender;
import com.konkuk.strhat.domain.diary.entity.Diary;
import com.konkuk.strhat.global.entity.BaseCreatedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chat_message")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage extends BaseCreatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_message_id", updatable = false)
    private Long id;

    @Column(name = "content", length = 255, nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "sender", length = 255, nullable = false)
    private Sender sender;

    @Enumerated(EnumType.STRING)
    @Column(name = "chat_mode", length = 20, nullable = false)
    private ChatMode chatMode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id", nullable = false)
    private Diary diary;

    @Builder
    public ChatMessage(String content, Sender sender, Diary diary, ChatMode chatMode) {
        this.content = content;
        this.sender = sender;
        this.diary = diary;
        this.chatMode = chatMode;
    }
}
