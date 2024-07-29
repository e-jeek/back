package com.ejeek.back.feed.dto;


import com.ejeek.back.feed.entity.Feed;
import com.ejeek.back.hashtag.HashtagReference;
import com.ejeek.back.image.ImageReference;
import com.ejeek.back.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FeedSaveDto {
    private long id;
    private Member member;
    private String content;


    private ImageReference.MappingType feedImage;
    private HashtagReference.MappingType feedHashtag;
    private long refId;

    @Builder
    public FeedSaveDto(long id, Member member, String content, ImageReference.MappingType feedImage, HashtagReference.MappingType feedHashtag, long refId) {
        this.id = id;
        this.member = member;
        this.content = content;
        this.feedImage = feedImage;
        this.feedHashtag = feedHashtag;
        this.refId = refId;
    }






}
