package com.ejeek.back.feed.dto;


import com.ejeek.back.feed.entity.Feed;
import com.ejeek.back.hashtag.HashtagReference;
import com.ejeek.back.image.ImageReference;
import com.ejeek.back.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedResponseDto {
    private Long id;
    private Member member;
    private String content;


    private ImageReference.MappingType feedImage;
    private HashtagReference.MappingType feedHashtag;
    private Long refId;


    public FeedResponseDto(Feed entity){
        this.id = entity.getId();
        this.member = entity.getMember();
        this.content = entity.getContent();
        this.feedHashtag = entity.getHashtagMappingType();
        this.feedImage = entity.getImageMappingType();
        this.refId = entity.getRefId();
    }
}
