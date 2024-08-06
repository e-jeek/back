package com.ejeek.back.feed.dto;

import com.ejeek.back.hashtag.HashtagReference;
import com.ejeek.back.image.ImageReference;
import com.ejeek.back.member.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedUpdateRequestDto {

    private Long id;
    private Member member;
    private String content;


    private ImageReference.MappingType feedImage;
    private HashtagReference.MappingType feedHashtag;
    private Long refId;

    @Builder
    public FeedUpdateRequestDto(String content, ImageReference.MappingType feedImage, HashtagReference.MappingType feedHashtag){
        this.content = content;
        this.feedImage = feedImage;
        this.feedHashtag = feedHashtag;
    }


}
