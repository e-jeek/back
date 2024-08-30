package com.ejeek.back.action.entity;

import com.ejeek.back.image.Image;
import com.ejeek.back.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("WAKEUP")
@PrimaryKeyJoinColumn(name = "action_id")
public class WakeupLog extends Action {

    @Column(nullable = false)
    private LocalTime wakeupTime;

    @Builder
    public WakeupLog(LocalDate date, Member member, Integer score, String content, LocalTime wakeupTime) {
        super(date, member, score, content);
        this.wakeupTime = wakeupTime;
    }

    public void setMember(Member member) {
        super.setMember(member);
    }

    public void updateImage(Image image) {
    }
}
