package com.ejeek.back.member.entity;

import com.ejeek.back.global.referable.ImageReferable;
import com.ejeek.back.image.ImageReference;
import com.ejeek.back.enums.Gender;
import com.ejeek.back.enums.MemberStatus;
import com.ejeek.back.enums.Role;
import com.ejeek.back.global.audit.Timestamped;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends Timestamped implements ImageReferable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    private String content;
    private LocalDate birth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Integer height;
    private Integer weight;

    @Column(nullable = false)
    private Boolean policy;

    @Column(nullable = false)
    private Boolean marketing;

    @Override
    public ImageReference.MappingType getImageMappingType() {
        return ImageReference.MappingType.MEMBER;
    }

    @Override
    public Long getRefId() {
        return this.id;
    }

    public void updateEncryptedPassword(String password) {
        this.password = password;
    }
    public void updateStatus() {
        this.status = MemberStatus.ACTIVE;
    }
    public void updateRole() {
        this.role = Role.USER;
    }

}