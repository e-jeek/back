package com.ejeek.back.action.entity;

import com.ejeek.back.member.Member;
import com.ejeek.back.enums.DietType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("DIET")
@PrimaryKeyJoinColumn(name = "action_id")
public class DietLog extends Action {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DietType dietType;

    @Column(length = 50, nullable = false)
    private String foodName;

    private Integer calories;

    @Builder
    public DietLog(Member member, Integer score, String content, DietType dietType, String foodName, Integer calories) {
        super(member, score, content);
        this.dietType = dietType;
        this.foodName = foodName;
        this.calories = calories;
    }

    public void setMember(Member member) {
        super.setMember(member);
    }
}
