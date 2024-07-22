package com.ejeek.back.member;

import com.ejeek.back.dto.MemberDto;
import com.ejeek.back.enums.Gender;
import com.ejeek.back.enums.MemberStatus;
import com.ejeek.back.enums.Role;
import com.ejeek.back.global.audit.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDate;

@Entity
//@Table(name = "member")
@Getter
@Setter
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

    public static Member createMember(MemberDto dto, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setEmail(dto.getEmail());
        member.setPassword(passwordEncoder.encode(dto.getPassword()));
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
