package com.ejeek.back.challenge;

import com.ejeek.back.enums.Capacity;
import com.ejeek.back.enums.ChallengeStatus;
import com.ejeek.back.enums.ChallengeType;
import com.ejeek.back.enums.Rule;
import com.ejeek.back.global.audit.Timestamped;
import com.ejeek.back.global.exception.CustomException;
import com.ejeek.back.global.exception.ExceptionCode;
import com.ejeek.back.global.referable.HashtagReferable;
import com.ejeek.back.global.referable.ImageReferable;
import com.ejeek.back.hashtag.Hashtag;
import com.ejeek.back.hashtag.HashtagReference;
import com.ejeek.back.image.ImageReference;
import com.ejeek.back.member.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class Challenge extends Timestamped implements ImageReferable, HashtagReferable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @NotNull
    private Member member;

    @Column(nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChallengeType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Capacity capacity;

    @Column(nullable = false)
    private LocalDateTime dueDate;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rule rule;

    @Column(nullable = false)
    private Boolean hidden;

    @Column(length = 100)
    private String secretKey;

    @Column(nullable = false, length = 500)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChallengeStatus status = ChallengeStatus.INACTIVE;

    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Hashtag> hashtags = new ArrayList<>();

    private String imgUrl;

    @Builder
    public Challenge(Member member, String name, ChallengeType type, Capacity capacity, LocalDateTime dueDate,
                    LocalDateTime startDate, LocalDateTime endDate, Rule rule, Boolean hidden, String secretKey,
                    String content) {
        this.member = member;
        this.name = name;
        this.type = type;
        this.capacity = capacity;
        validateDateTime(dueDate, startDate, endDate);
        this.dueDate = dueDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rule = rule;
        this.hidden = hidden;
        validateSecretKey(secretKey);
        this.content = content;
    }

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

    public void updateChallengeByDto(ChallengeDto.Request request) {
        this.name = request.getName();
        this.type = request.getType();
        this.capacity = request.getCapacity();
        validateDateTime(request.getDueDate(), request.getStartDate(), request.getEndDate());
        this.dueDate = request.getDueDate();
        this.startDate = request.getStartDate();
        this.endDate = request.getEndDate();
        this.rule = request.getRule();
        this.hidden = request.getHidden();
        validateSecretKey(request.getSecretKey());
        this.content = request.getContent();
    }

    public void updateStatus(ChallengeStatus status) {
        this.status = status;
    }

    public void updateImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void updateSecretKey(String secretKey) {
        validateSecretKey(secretKey);
    }

    public void addHashtag(Hashtag hashtag) {
        hashtags.add(hashtag);
        hashtag.updateChallenge(this);
    }

    public void clearHashtags() {
        this.hashtags.clear();
    }

    public void updateHashtags(List<Hashtag> hashtags) {
        clearHashtags();
        hashtags.forEach(this::addHashtag);
    }

    public void checkEditableOrDeletable() {
        if (status == ChallengeStatus.ACTIVE || status == ChallengeStatus.FINISH) {
            throw new CustomException(ExceptionCode.CHALLENGE_CANNOT_MODIFY);
        }
    }

    private void validateDateTime(LocalDateTime dueDate, LocalDateTime startDate, LocalDateTime endDate) {
        LocalDateTime now = LocalDateTime.now();
        if (dueDate.isBefore(now) || startDate.isBefore(now) || endDate.isBefore(now)) {
            throw new CustomException(ExceptionCode.CHALLENGE_DATE_ERROR);
        }
        if (dueDate.isAfter(startDate)) {
            throw new CustomException(ExceptionCode.CHALLENGE_DUEDATE_ERROR);
        }
        if (startDate.isAfter(endDate)) {
            throw new CustomException(ExceptionCode.CHALLENGE_STARTDATE_ERROR);
        }
    }

    private void validateSecretKey(String secretKey) {
        if (hidden) {
            if (secretKey == null || secretKey.isEmpty())  throw new CustomException(ExceptionCode.INVALID_SECRET_KEY);
            this.secretKey = secretKey;
        }
    }
}
