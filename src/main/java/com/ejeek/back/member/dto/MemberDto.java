package com.ejeek.back.member.dto;

import com.ejeek.back.enums.Gender;
import com.ejeek.back.enums.MemberStatus;
import com.ejeek.back.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class MemberDto {

    @Getter
    @AllArgsConstructor
    public static class LoginRequest{
        @NotEmpty
        private String email;

        @NotEmpty
        private String password;
    }

    @Getter
    @NoArgsConstructor
    public static class SignupRequest {

        @NotEmpty(message = "이메일은 필수 입력 값입니다.")
        @Email(message = "이메일 형식으로 입력해주세요")
        private String email;

        @NotEmpty(message = "이름은 필수 입력 값입니다.")
        private String name;

        @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
        private String password;

        @NotEmpty(message = "닉네임은 필수 입력 값입니다.")
        private String nickname;

        private String content;
        private LocalDate birth;
        private Gender gender;
        private Integer height;
        private Integer weight;

        @NotNull
        private Boolean policy;

        @NotNull
        private Boolean marketing;

    }

    @Getter
    @AllArgsConstructor
    public static class SimpleResponse {
        private Long id;
        private String email;
        private String name;
        private String nickname;
        private MemberStatus status;
        private Role role;

    }
}