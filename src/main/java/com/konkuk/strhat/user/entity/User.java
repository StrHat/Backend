package com.konkuk.strhat.user.entity;

import com.konkuk.strhat.diary.entity.Diary;
import com.konkuk.strhat.global.entity.BaseCreatedEntity;
import com.konkuk.strhat.self_diagnosis.entity.SelfDiagnosis;
import com.konkuk.strhat.stress_summary.entity.StressSummary;
import com.konkuk.strhat.user.enums.Gender;
import com.konkuk.strhat.user.enums.Job;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.time.LocalDate;
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

    @Size(max = 10, message = "닉네임은 10자 이하로 입력해주세요.")
    @Column(name = "nickname", length = 10, nullable = false)
    private String nickname;

    @Column(name = "birth", nullable = false)
    private LocalDate birth;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 10, nullable = false)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "job", length = 10, nullable = false)
    private Job job;

    @Size(max = 1000, message = "취미 및 힐링방법은 1000자 이하로 입력해주세요.")
    @Column(name = "hobby_healing_style", length = 1000, nullable = false)
    private String hobbyHealingStyle;

    @Size(max = 1000, message = "스트레스 해소 방법은 1000자 이하로 입력해주세요.")
    @Column(name = "stress_relief_style", length = 1000, nullable = false)
    private String stressReliefStyle;

    @Size(max = 1000, message = "성향 정보는 1000자 이하로 입력해주세요.")
    @Column(name = "personality", length = 1000, nullable = false)
    private String personality;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SelfDiagnosis> selfDiagnoses = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Diary> diaries = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StressSummary> stressSummaries = new ArrayList<>();


    @Builder
    public User(String nickname, LocalDate birth, Gender gender, Job job,
                String hobbyHealingStyle, String stressReliefStyle, String personality) {
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

}