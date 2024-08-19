package com.ejeek.back.action.entity;

import com.ejeek.back.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("WAKEUP")
@PrimaryKeyJoinColumn(name = "action_id")
public class WakeupLog extends Action {

    @Column(nullable = false)
    private LocalDateTime wakeupTime;

    @Builder
    public WakeupLog(Member member, Integer score, String content, LocalDateTime wakeupTime) {
        super(member, score, content);
        this.wakeupTime = wakeupTime;
    }

    public void setMember(Member member) {
        super.setMember(member);
    }

}
