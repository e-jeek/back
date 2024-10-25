package com.ejeek.back.feed.dto;


import com.ejeek.back.hashtag.Hashtag;
import com.ejeek.back.member.dto.MemberDto;
import com.ejeek.back.member.entity.Member;
import lombok.*;

import java.util.List;

public class FeedDto {

    @Getter
    @NoArgsConstructor
    public static class FeedRequest {

        private String content;
        private List<String> hashtags;

    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FeedResponse {

        private final Long id;
        private final MemberDto.SimpleResponse member;
        private final String content;
        private final String imageUrl;
        private final List<String> hashtags;

    }

    @Getter
    @NoArgsConstructor
    public static class FeedUpdateRequest {
        private Long id;
        private String content;
        private List<String> hashtags;

    }
}