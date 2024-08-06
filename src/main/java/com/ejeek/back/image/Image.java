package com.ejeek.back.image;

import com.ejeek.back.action.Action;
import com.ejeek.back.challenge.Challenge;
import com.ejeek.back.challenge.challenge_confirm.ChallengeConfirm;
import com.ejeek.back.feed.Feed;
import com.ejeek.back.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MappingType type;

    @Column(nullable = false)
    private Long refId;

    @Column(nullable = false, length = 2500)
    private String url;

    public Image(MappingType type, Long refId, String url) {
        this.type = type;
        this.refId = refId;
        this.url = url;
    }

    @Getter
    @AllArgsConstructor
    public enum MappingType {
        MEMBER(Member.class),
        ACTION(Action.class),
        CHALLENGE(Challenge.class),
        FEED(Feed.class),
        CHALLENGE_CONFIRM(ChallengeConfirm.class);

        private final Class<?> mappingTypeClass;
    }
}
