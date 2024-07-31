package com.ejeek.back.challenge.challenge_confirm;

import com.ejeek.back.challenge.Challenge;
import com.ejeek.back.global.audit.Timestamped;
import com.ejeek.back.global.referable.ImageReferable;
import com.ejeek.back.image.Image;
import com.ejeek.back.image.ImageReference;
import com.ejeek.back.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ChallengeConfirm extends Timestamped implements ImageReferable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;
    private Boolean confirmed;
    private String content;

    @Override
    public ImageReference.MappingType getImageMappingType() {
        return ImageReference.MappingType.CHALLENGE_CONFIRM;
    }

    @Override
    public Long getRefId() {
        return this.id;
    }
}
