package com.ejeek.back.feed.entity;

import com.ejeek.back.global.audit.Timestamped;
import com.ejeek.back.global.referable.HashtagReferable;
import com.ejeek.back.global.referable.ImageReferable;
import com.ejeek.back.hashtag.Hashtag;
import com.ejeek.back.hashtag.HashtagReference;
import com.ejeek.back.image.ImageReference;
import com.ejeek.back.member.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Feed extends Timestamped implements ImageReferable, HashtagReferable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    private String content;

    @Override
    public ImageReference.MappingType getImageMappingType() {
        return ImageReference.MappingType.FEED;
    }

    @Override
    public HashtagReference.MappingType getHashtagMappingType() {
        return HashtagReference.MappingType.FEED;
    }

    @Override
    public Long getRefId() {
        return this.id;
    }

    @Builder
    public Feed(Long id, Member member, String content, ImageReference.MappingType feedImage, HashtagReference.MappingType feedHashtag, long refId ) {
        this.id = id;
        this.member = member;
        this.content = content;

    }


}
