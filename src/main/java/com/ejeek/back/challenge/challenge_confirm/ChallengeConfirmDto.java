package com.ejeek.back.challenge.challenge_confirm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class ChallengeConfirmDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {

        private String content;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {

        private final Long id;
        private final Long memberId;
        private final Long challengeId;
        private final Boolean confirmed;
        private final String content;
        private final String imageUrl;
        private final LocalDateTime createdAt;
        private final LocalDateTime updateAt;
    }
}
