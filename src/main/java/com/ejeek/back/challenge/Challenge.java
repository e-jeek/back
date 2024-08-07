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
import com.ejeek.back.image.Image;
import com.ejeek.back.member.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "image_id")
    private Image image;

    @Builder
    public Challenge(Member member, String name, ChallengeType type, Capacity capacity, LocalDateTime dueDate,
                    LocalDateTime startDate, LocalDateTime endDate, Rule rule, Boolean hidden, String secretKey, String content) {
        this.member = member;
        this.name = name;
        this.type = type;
        this.capacity = capacity;
        this.rule = rule;
        this.content = content;
        validateDateTime(dueDate, startDate, endDate);
        this.dueDate = dueDate;
        this.startDate = startDate;
        this.endDate = endDate;
        validateSecretKey(hidden, secretKey);
        this.hidden = hidden;
        if (hidden)
            this.secretKey = secretKey;
    }

    @Override
    public Image.MappingType getImageMappingType() {
        return Image.MappingType.CHALLENGE;
    }

    @Override
    public Hashtag.MappingType getHashtagMappingType() {
        return Hashtag.MappingType.CHALLENGE;
    }

    @Override
    public Long getRefId() {
        return this.id;
    }

    public void encryptSecretKey(PasswordEncoder passwordEncoder) {
        if (this.secretKey != null && !this.secretKey.isEmpty()) {
            this.secretKey = passwordEncoder.encode(this.secretKey);
        }
    }

    public void updateChallengeByDto(ChallengeDto.Request request) {
        this.name = request.getName();
        this.type = request.getType();
        this.capacity = request.getCapacity();
        this.rule = request.getRule();
        this.content = request.getContent();
        validateDateTime(request.getDueDate(), request.getStartDate(), request.getEndDate());
        this.dueDate = request.getDueDate();
        this.startDate = request.getStartDate();
        this.endDate = request.getEndDate();
        validateSecretKey(request.getHidden(), request.getSecretKey());
        this.hidden = request.getHidden();
        if (request.getHidden())
            this.secretKey = request.getSecretKey();
    }

    public void updateStatus(ChallengeStatus status) {
        this.status = status;
    }

    public void updateImgUrl(Image image) {
        this.image = image;
    }

    public void updateHashtags(List<Hashtag> hashtags) {
        this.hashtags.clear();
        for (Hashtag hashtag : hashtags) {
            this.hashtags.add(hashtag);
            hashtag.updateChallenge(this);
        }
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

    private void validateSecretKey(Boolean hidden, String secretKey) {
        if (hidden && (secretKey == null || secretKey.isEmpty())) {
            throw new CustomException(ExceptionCode.SECRET_KEY_REQUIRED);
        }
        if (!hidden && (secretKey != null && !secretKey.isEmpty())) {
            throw new CustomException(ExceptionCode.SECRET_KEY_NOT_ALLOWED);
        }
    }
}
