package com.ejeek.back.action.entity;

import com.ejeek.back.global.audit.Timestamped;
import com.ejeek.back.global.referable.ImageReferable;
import com.ejeek.back.image.Image;
import com.ejeek.back.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
public class Action extends Timestamped implements ImageReferable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    protected LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    protected Member member;

    @Column(nullable = false)
    protected Integer score;

    @Column(length = 500)
    protected String content;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "image_id")
    protected Image image;

    @Override
    public Image.MappingType getImageMappingType() {
        return Image.MappingType.ACTION;
    }

    @Override
    public Long getRefId() {
        return this.id;
    }

    public void updateImage(Image image) {
        this.image = image;
    }


    public Action(LocalDate date, Member member, Integer score, String content) {
        this.date = date;
        this.member = member;
        this.score = score;
        this.content = content;
    }

}
