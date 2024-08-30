package com.ejeek.back.action.entity;

import com.ejeek.back.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("EXERCISE")
@PrimaryKeyJoinColumn(name = "action_id")
public class ExerciseLog extends Action {

    @Column(length = 50, nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer duration;

    private Integer calories;

    @Builder
    public ExerciseLog(LocalDate date, Member member, Integer score, String content, String name, Integer duration, Integer calories) {
        super(date, member, score, content); // 부모 클래스의 빌더 생성자 호출
        this.name = name;
        this.duration = duration;
        this.calories = calories;
    }

    public void setMember(Member member) {
        super.setMember(member);
    }
}
