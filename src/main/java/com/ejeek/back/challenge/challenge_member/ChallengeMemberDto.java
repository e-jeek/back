package com.ejeek.back.challenge.challenge_member;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChallengeMemberDto {

    private final Long id;
    private final Long challengeId;
    private final Long memberId;
}
