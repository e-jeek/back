package com.ejeek.back.member.entity;

import com.ejeek.back.member.dto.MemberDto;
import com.ejeek.back.enums.Gender;
import com.ejeek.back.enums.MemberStatus;
import com.ejeek.back.enums.Role;
import com.ejeek.back.global.audit.Timestamped;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
//@Table(name = "member")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member extends Timestamped {

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

    public static Member createMember(MemberDto dto) {
        Member member = new Member();
        member.setEmail(dto.getEmail());
        member.setPassword(dto.getPassword());
        member.setName(dto.getName());
        member.setNickname(dto.getNickname());
        member.setStatus(dto.getStatus());
        member.setRole(Role.USER);
        member.setContent(dto.getContent());
        member.setBirth(dto.getBirth());
        member.setGender(dto.getGender());
        member.setHeight(dto.getHeight());
        member.setWeight(dto.getWeight());
        member.setPolicy(dto.getPolicy());
        member.setMarketing(dto.getMarketing());
        return member;
    }
}
