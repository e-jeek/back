package com.ejeek.back.challenge.challenge_confirm;

import com.ejeek.back.challenge.Challenge;
import com.ejeek.back.global.audit.Timestamped;
import com.ejeek.back.global.referable.ImageReferable;
import com.ejeek.back.image.Image;
import com.ejeek.back.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChallengeConfirm extends Timestamped implements ImageReferable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    @Column(nullable = false)
    private Boolean confirmed = false;

    @Column(length = 500)
    private String content;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "image_id")
    private Image image;

    @Builder
    public ChallengeConfirm(Member member, Challenge challenge, String content) {
        this.member = member;
        this.challenge = challenge;
        this.content = content;
    }

    public void updateImage(Image image) {
        this.image = image;
    }

    @Override
    public Image.MappingType getImageMappingType() {
        return Image.MappingType.CHALLENGE_CONFIRM;
    }

    @Override
    public Long getRefId() {
        return this.id;
    }
}
