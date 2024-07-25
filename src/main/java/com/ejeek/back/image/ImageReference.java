package com.ejeek.back.image;

import com.ejeek.back.action.Action;
import com.ejeek.back.challenge.Challenge;
import com.ejeek.back.feed.Feed;
import com.ejeek.back.member.entity.Member;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Embeddable
public class ImageReference {

    @Enumerated(EnumType.STRING)
    private MappingType type;
    private Long refId;

    @Getter
    @AllArgsConstructor
    public enum MappingType {

        MEMBER(Member.class),
        ACTION(Action.class),
        CHALLENGE(Challenge.class),
        FEED(Feed.class);

        MappingType(Class<?> mappingTypeClass) {
        }
    }
}
