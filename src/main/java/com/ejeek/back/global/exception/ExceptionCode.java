package com.ejeek.back.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionCode {

    CONNECTION_ERROR(501, "연결 오류"),
    MEMBER_EMAIL_EXIST(409, "이미 사용 중인 이메일 입니다."),
    MEMBER_USERNAME_EXIST(409, "이미 사용 중인 닉네임 입니다."),
    MEMBER_NOT_EXIST(408, "가입된 이메일 정보가 없습니다."),
    MEMBER_PASSWORD_INCORRECT(408, "비밀번호 정보가 다릅니다.");

    private final int code;
    private final String message;
}
