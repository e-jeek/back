package com.ejeek.back.image;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Image {

    @EmbeddedId
    private ImageReference reference;
    private String url;
}
