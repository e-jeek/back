package com.ejeek.back.comment.entity;

import com.ejeek.back.comment.dto.CommentDto;
import com.ejeek.back.feed.entity.Feed;
import com.ejeek.back.global.audit.Timestamped;
import com.ejeek.back.member.entity.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "feed_id")
    private Feed feed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    private String content;

    @Builder
    public Comment(Member member, Feed feed, Comment parentComment, String content) {
        this.member = member;
        this.feed = feed;
        this.parentComment = parentComment;
        this.content = content;
    }

    public void updateCommentDto(CommentDto.CommentUpdateRequest request) {
        this.parentComment = request.getParentComment();
        this.content = request.getContent();
    }

    public boolean canModifiedBy(Member member, Feed feed) {
        boolean canModify = false;
        Member feedOwner = this.feed.getMember();
        if(this.member.equals(member.getId()) || this.member.equals(feedOwner)) {
            canModify = true;
        }


        return canModify;
    }



}
