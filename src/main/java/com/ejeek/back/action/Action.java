package com.ejeek.back.action;

import com.ejeek.back.global.audit.Timestamped;
import com.ejeek.back.global.referable.ImageReferable;
import com.ejeek.back.image.ImageReference;
import com.ejeek.back.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
public class Action extends Timestamped implements ImageReferable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    private Integer score;
    private String content;

    @Override
    public ImageReference.MappingType getImageMappingType() {
        return ImageReference.MappingType.ACTION;
    }

    @Override
    public Long getRefId() {
        return this.id;
    }
}
