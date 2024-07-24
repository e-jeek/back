package com.ejeek.back.member;

import com.ejeek.back.enums.Gender;
import com.ejeek.back.enums.MemberStatus;
import com.ejeek.back.global.audit.Timestamped;
import com.ejeek.back.global.referable.ImageReferable;
import com.ejeek.back.image.ImageReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Member extends Timestamped implements ImageReferable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String name;
    private String nickname;
    @Enumerated(EnumType.STRING)
    private MemberStatus status = MemberStatus.ACTIVE;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();
    private String content;
    private LocalDate birth;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Integer height;
    private Integer weight;
    private Boolean policy;
    private Boolean marketing;

    @Override
    public ImageReference.MappingType getImageMappingType() {
        return ImageReference.MappingType.MEMBER;
    }

    @Override
    public Long getRefId() {
        return this.id;
    }
}
