package com.ejeek.back.image;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Image {

    @EmbeddedId
    private ImageReference type;
    private String url;
}
