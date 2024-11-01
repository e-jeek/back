package com.ejeek.back.comment.dto;

import com.ejeek.back.comment.entity.Comment;
import com.ejeek.back.feed.dto.FeedDto;
import com.ejeek.back.member.dto.MemberDto;
import lombok.*;

public class CommentDto {

    @Getter
    @NoArgsConstructor
    public static class CommentRequest {

        private String content;
        private Comment parentComment;

    }


    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CommentResponse {

        private final Long id;
        private final MemberDto.SimpleResponse member;
        private final String content;
        private final FeedDto.FeedResponse feed;

    }

    @Getter
    @NoArgsConstructor
    public static class CommentUpdateRequest {
        private Long id;
        private String content;
        private Comment parentComment;

    }




}
