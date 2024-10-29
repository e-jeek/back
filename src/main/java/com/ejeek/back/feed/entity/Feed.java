package com.ejeek.back.feed.entity;

import com.ejeek.back.feed.dto.FeedDto;
import com.ejeek.back.global.audit.Timestamped;
import com.ejeek.back.global.referable.HashtagReferable;
import com.ejeek.back.global.referable.ImageReferable;
import com.ejeek.back.hashtag.Hashtag;
import com.ejeek.back.image.Image;
import com.ejeek.back.member.entity.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class Feed extends Timestamped implements ImageReferable, HashtagReferable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @NotEmpty(message = "내용을 입력하세요")
    @Column(nullable = false, length = 500)
    private String content;

    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Hashtag> hashtags = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;


    @Override
    public Image.MappingType getImageMappingType() {
        return Image.MappingType.FEED;
    }

    @Override
    public Hashtag.MappingType getHashtagMappingType() {
        return Hashtag.MappingType.FEED;
    }

    @Builder
    public Feed(Member member, String content) {
        this.member = member;
        this.content = content;
    }

    @Override
    public Long getRefId() {
        return this.id;
    }

    public void updateFeedDto(FeedDto.FeedUpdateRequest request){
        this.content = request.getContent();
    }



    public void updateImgUrl(Image image) {
        this.image = image;
    }

    public void updateHashtags(List<Hashtag> hashtags) {
        this.hashtags.clear();
        for (Hashtag hashtag : hashtags) {
            this.hashtags.add(hashtag);
            hashtag.updateFeed(this);
        }
    }

    public boolean canModifiedBy(Member member) {
        return this.member.getId().equals(member.getId());
    }

}