package com.ejeek.back.challenge.challenge_member;

import com.ejeek.back.challenge.Challenge;
import com.ejeek.back.global.audit.Timestamped;
import com.ejeek.back.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChallengeMember extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    public ChallengeMember(Member member, Challenge challenge) {
        this.member = member;
        this.challenge = challenge;
    }
}
