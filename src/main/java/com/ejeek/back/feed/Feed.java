package com.ejeek.back.feed;

import com.ejeek.back.global.audit.Timestamped;
import com.ejeek.back.global.referable.HashtagReferable;
import com.ejeek.back.global.referable.ImageReferable;
import com.ejeek.back.hashtag.Hashtag;
import com.ejeek.back.image.Image;
import com.ejeek.back.member.Member;
import jakarta.persistence.*;
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
    public Image.MappingType getImageMappingType() {
        return Image.MappingType.FEED;
    }

    @Override
    public Hashtag.MappingType getHashtagMappingType() {
        return Hashtag.MappingType.FEED;
    }

    @Override
    public Long getRefId() {
        return this.id;
    }
}
