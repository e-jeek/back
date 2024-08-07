package com.ejeek.back.challenge.challenge_member;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

public class ChallengeMemberDto {

    @Getter
    @AllArgsConstructor
    public static class Response {

        private final Long id;
        private final Long challengeId;
        private final Long memberId;
        private final LocalDateTime createdAt;
        private final LocalDateTime updateAt;
    }

}
