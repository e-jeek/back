package com.ejeek.back.hashtag;

import com.ejeek.back.challenge.Challenge;
import com.ejeek.back.feed.Feed;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class HashtagReference implements Serializable {

    private MappingType type;
    private Long refId;

    @Getter
    @AllArgsConstructor
    public enum MappingType {
        CHALLENGE(Challenge.class),
        FEED(Feed.class);

        MappingType(Class<?> mappingTypeClass) {
        }
    }
}
