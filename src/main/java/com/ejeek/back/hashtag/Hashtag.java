package com.ejeek.back.hashtag;

import com.ejeek.back.challenge.Challenge;
import com.ejeek.back.feed.Feed;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Getter
@NoArgsConstructor
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Hashtag.MappingType type;

    @Column(name = "ref_id", nullable = false)
    private Long refId;

    @Column(nullable = false)
    private String tagName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ref_id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private Challenge challenge;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ref_id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private Feed feed;

    @Getter
    @AllArgsConstructor
    public enum MappingType {
        CHALLENGE(Challenge.class),
        FEED(Feed.class);

        MappingType(Class<?> mappingTypeClass) {
        }
    }

    public Hashtag(MappingType type, Long refId, String tagName) {
        this.type = type;
        this.refId = refId;
        this.tagName = tagName;
    }

    public void updateChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

    public void updateFeed(Feed feed) {
        this.feed = feed;
    }
}
