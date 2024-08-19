package com.ejeek.back.action.entity;

import com.ejeek.back.global.audit.Timestamped;
import com.ejeek.back.global.referable.ImageReferable;
import com.ejeek.back.image.ImageReference;
import com.ejeek.back.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
public class Action extends Timestamped implements ImageReferable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private Integer score;

    @Column(length = 500)
    private String content;

    @Override
    public ImageReference.MappingType getImageMappingType() {
        return ImageReference.MappingType.ACTION;
    }

    @Override
    public Long getRefId() {
        return this.id;
    }


    public Action(Member member, Integer score, String content) {
        this.member = member;
        this.score = score;
        this.content = content;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
