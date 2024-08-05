package com.ejeek.back.challenge;

import com.ejeek.back.enums.Capacity;
import com.ejeek.back.enums.ChallengeStatus;
import com.ejeek.back.enums.ChallengeType;
import com.ejeek.back.enums.Rule;
import com.ejeek.back.member.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class ChallengeDto {

    @Data
    @Getter
    @NoArgsConstructor
    public static class Request {

        private String name;
        private ChallengeType type;
        private Capacity capacity;
        private LocalDateTime dueDate;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private Rule rule;
        private Boolean hidden;
        private String secretKey;
        private String content;
        private List<String> hashtags;
    }

    @Data
    @Getter
    @AllArgsConstructor
    public static class Response {

        private final Long id;
        private final MemberDto.Response member;
        private final String name;
        private final ChallengeType type;
        private final Capacity capacity;
        private final LocalDateTime dueDate;
        private final LocalDateTime startDate;
        private final LocalDateTime endDate;
        private final Rule rule;
        private final Boolean hidden;
        private final String secretKey;
        private final String content;
        private final ChallengeStatus status;
        private final List<String> hashtags;
        private final String imgUrl;
        private final LocalDateTime createdAt;
        private final LocalDateTime updateAt;
    }
}
