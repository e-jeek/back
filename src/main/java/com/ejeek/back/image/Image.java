package com.ejeek.back.image;

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
    @Column(name = "type", nullable = false)
    private ImageReference.MappingType type;

    @Column(name = "ref_id", nullable = false)
    private Long refId;

    private String url;

    public Image(ImageReference reference, String url) {
        this.type = reference.getType();
        this.refId = reference.getRefId();
        this.url = url;
    }
}
