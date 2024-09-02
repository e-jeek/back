package com.ejeek.back.action.entity;

import com.ejeek.back.action.dto.ExerciseLogDto;
import com.ejeek.back.action.dto.WakeupLogDto;
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
        super(date, member, score, content);
        this.name = name;
        this.duration = duration;
        this.calories = calories;
    }

    public void updateExerciseLog(ExerciseLogDto.UpdateRequest request) {
        this.date = request.getDate();
        this.content = request.getContent();
        this.score = request.getScore();
        this.name = request.getName();
        this.duration = request.getDuration();
        this.calories = request.getCalories();

    }

}
