package com.ejeek.back.challenge.challenge_member;

import com.ejeek.back.challenge.Challenge;
import com.ejeek.back.global.audit.Timestamped;
import com.ejeek.back.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ChallengeMember extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;
}
