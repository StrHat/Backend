package com.konkuk.strhat.domain.diary.application;

import com.konkuk.strhat.domain.diary.dao.DiaryRepository;
import com.konkuk.strhat.domain.diary.dto.CheckDiaryResponse;
import com.konkuk.strhat.domain.diary.entity.Diary;
import com.konkuk.strhat.domain.user.dao.UserRepository;
import com.konkuk.strhat.domain.user.entity.User;
import com.konkuk.strhat.domain.user.exception.NotFoundUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

import static com.konkuk.strhat.global.util.DateParser.parseToLocalDate;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;

    public CheckDiaryResponse checkDiaryExist(Long currentUserId, String date){
        User user = userRepository.findById(currentUserId)
                .orElseThrow(NotFoundUserException::new);

        Optional<Diary> diary = diaryRepository.findByDiaryDateAndUser(parseToLocalDate(date), user);

        CheckDiaryResponse response;
        if(diary.isPresent()){
            Integer emotion = diary.get().getEmotion();
            String content = diary.get().getContent();
            response = CheckDiaryResponse.builder()
                    .hasDiary(true)
                    .emotion(emotion)
                    .summary(content.substring(0, Math.min(content.length(), 70)))
                    .build();
        } else{
            response = CheckDiaryResponse.builder()
                    .hasDiary(false)
                    .emotion(null)
                    .summary(null)
                    .build();
        }

        return response;
    }

}
