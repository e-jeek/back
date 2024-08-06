package com.ejeek.back.feed.dto;


import com.ejeek.back.feed.entity.Feed;
import com.ejeek.back.hashtag.HashtagReference;
import com.ejeek.back.image.ImageReference;
import com.ejeek.back.member.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedSaveRequestDto {
    private Long id;
    private Member member;
    private String content;


    private ImageReference.MappingType feedImage;
    private HashtagReference.MappingType feedHashtag;
    private Long refId;

    @Builder
    public FeedSaveRequestDto(Long id, Member member, String content, ImageReference.MappingType feedImage, HashtagReference.MappingType feedHashtag, Long refId) {
        this.id = id;
        this.member = member;
        this.content = content;
        this.feedImage = feedImage;
        this.feedHashtag = feedHashtag;
        this.refId = refId;
    }

    public Feed toEntity(){
        return Feed.builder()
                .member(member)
                .content(content)
                .feedImage(feedImage)
                .feedHashtag(feedHashtag)
                .refId(refId)
                .build();
    }








}
