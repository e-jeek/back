package com.ejeek.back.hashtag;

import com.ejeek.back.challenge.Challenge;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Hashtag {

    @EmbeddedId
    private HashtagReference reference;

    @ManyToOne
    @MapsId("refId")
    @JoinColumn(name="ref_id")
    private Challenge challenge;

    public Hashtag(HashtagReference reference) {
        this.reference = reference;
    }

    public void updateChallenge(Challenge challenge) {
        this.challenge = challenge;
    }
}
