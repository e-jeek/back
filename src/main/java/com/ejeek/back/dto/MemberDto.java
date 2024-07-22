package com.ejeek.back.dto;

import com.ejeek.back.enums.Gender;
import com.ejeek.back.enums.MemberStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class MemberDto {
    private Long id;
    @NotEmpty(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식으로 입력해주세요")
    private String email;
    private String name;
    private String password;
    private String nickname;
    private MemberStatus status;
    private String content;
    private LocalDate birth;
    private Gender gender;
    private Integer height;
    private Integer weight;
    private Boolean policy;
    private Boolean marketing;
}