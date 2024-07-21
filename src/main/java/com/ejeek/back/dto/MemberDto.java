package com.ejeek.back.dto;

import com.ejeek.back.enums.Gender;
import com.ejeek.back.enums.MemberStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MemberDto {
    private Long id;
    private String email;
    private String name;
    private String password;
    private String nickname;
    private MemberStatus status;
    private List<String> roles;
    private String content;
    private LocalDate birth;
    private Gender gender;
    private Integer height;
    private Integer weight;
    private Boolean policy;
    private Boolean marketing;
}