package com.ejeek.back.hashtag;

import com.ejeek.back.challenge.Challenge;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HashtagReference.MappingType type;

    @Column(name = "ref_id", nullable = false)
    private Long refId;

    @Column(nullable = false)
    private String tagName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ref_id", insertable = false, updatable = false)
    private Challenge challenge;

    public Hashtag(HashtagReference hashtagReference, String tagName) {
        this.type = hashtagReference.getType();
        this.refId = hashtagReference.getRefId();
        this.tagName = tagName;
    }

    public void updateChallenge(Challenge challenge) {
        this.challenge = challenge;
    }
}
