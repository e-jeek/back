package com.ejeek.back.challenge;

import com.ejeek.back.enums.Capacity;
import com.ejeek.back.enums.ChallengeStatus;
import com.ejeek.back.enums.ChallengeType;
import com.ejeek.back.enums.Rule;
import com.ejeek.back.global.audit.Timestamped;
import com.ejeek.back.global.referable.HashtagReferable;
import com.ejeek.back.global.referable.ImageReferable;
import com.ejeek.back.hashtag.HashtagReference;
import com.ejeek.back.image.ImageReference;
import com.ejeek.back.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Challenge extends Timestamped implements ImageReferable, HashtagReferable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    private String name;
    @Enumerated(EnumType.STRING)
    private ChallengeType type;
    @Enumerated(EnumType.STRING)
    private Capacity capacity;
    private LocalDateTime dueDate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @Enumerated(EnumType.STRING)
    private Rule rule;
    private Boolean hidden;
    private String secretKey;
    @Enumerated(EnumType.STRING)
    private ChallengeStatus status;
    private String content;
    private String chatUUID;

    @Override
    public ImageReference.MappingType getImageMappingType() {
        return ImageReference.MappingType.CHALLENGE;
    }

    @Override
    public HashtagReference.MappingType getHashtagMappingType() {
        return HashtagReference.MappingType.CHALLENGE;
    }

    @Override
    public Long getRefId() {
        return this.id;
    }
}
