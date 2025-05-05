package com.konkuk.strhat.domain.user.entity;

import com.konkuk.strhat.domain.diary.entity.Diary;
import com.konkuk.strhat.domain.user.enums.Gender;
import com.konkuk.strhat.domain.user.enums.Job;
import com.konkuk.strhat.domain.selfDiagnosis.entity.SelfDiagnosis;
import com.konkuk.strhat.global.entity.BaseCreatedEntity;
import com.konkuk.strhat.domain.stressSummary.entity.StressSummary;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseCreatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "nickname", length = 10, nullable = false)
    private String nickname;

    @Column(name = "birth", nullable = false, columnDefinition = "YEAR")
    private Integer birth;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 10, nullable = false)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "job", length = 10, nullable = false)
    private Job job;

    @Column(name = "hobby_healing_style", length = 1000, nullable = false)
    private String hobbyHealingStyle;

    @Column(name = "stress_relief_style", length = 1000, nullable = false)
    private String stressReliefStyle;

    @Column(name = "personality", length = 1000, nullable = false)
    private String personality;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SelfDiagnosis> selfDiagnoses = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Diary> diaries = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StressSummary> stressSummaries = new ArrayList<>();


    @Builder
    public User(String email, String nickname, Integer birth, Gender gender, Job job,
                String hobbyHealingStyle, String stressReliefStyle, String personality) {
        this.email = email;
        this.nickname = nickname;
        this.birth = birth;
        this.gender = gender;
        this.job = job;
        this.hobbyHealingStyle = hobbyHealingStyle;
        this.stressReliefStyle = stressReliefStyle;
        this.personality = personality;
        this.selfDiagnoses = new ArrayList<>();
        this.diaries = new ArrayList<>();
        this.stressSummaries = new ArrayList<>();
    }

    // 비즈니스 로직 메소드
    public void updateBasicInfo(String nickname, Integer birth, Gender gender, Job job) {
        this.nickname = nickname;
        this.birth = birth;
        this.gender = gender;
        this.job = job;
    }

    public void updateHobbyHealingStyle(String hobbyHealingStyle) {
        this.hobbyHealingStyle = hobbyHealingStyle;
    }

    public void updateStressReliefStyle(String stressReliefStyle) {
        this.stressReliefStyle = stressReliefStyle;
    }

    public void updatePersonality(String personality) {
        this.personality = personality;
    }

    // 연관관계 편의 메서드
    public void addSelfDiagnosis(SelfDiagnosis selfDiagnosis) {
        selfDiagnoses.add(selfDiagnosis);
        selfDiagnosis.setUser(this);
    }

    public void addDiary(Diary diary) {
        diaries.add(diary);
        diary.setUser(this);
    }

    public void addStressSummary(StressSummary stressSummary) {
        stressSummaries.add(stressSummary);
        stressSummary.setUser(this);
    }
}