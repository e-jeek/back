package com.ejeek.back.hashtag;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Hashtag {

    @EmbeddedId
    private HashtagReference reference;
    private String tagName;
}
