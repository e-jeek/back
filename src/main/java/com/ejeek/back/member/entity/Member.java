package com.ejeek.back.member.entity;

import com.ejeek.back.global.referable.ImageReferable;
import com.ejeek.back.image.ImageReference;
import com.ejeek.back.member.dto.MemberDto;
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
    //    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Role role;

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

    public static Member createMember(MemberDto dto) {
        Member member = new Member();

        member.email = dto.getEmail();
        member.password = dto.getPassword();
        member.name = dto.getName();
        member.nickname = dto.getNickname();
        member.status = dto.getStatus();
        member.role = Role.USER;
        member.content = dto.getContent();
        member.birth = dto.getBirth();
        member.gender = dto.getGender();
        member.height = dto.getHeight();
        member.weight = dto.getWeight();
        member.policy = dto.getPolicy();
        member.marketing = dto.getMarketing();

        return member;
    }

    public void setEncryptedPassword(String password) {
        this.password = password;
    }
}
