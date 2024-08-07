package com.ejeek.back.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionCode {

    CONNECTION_ERROR(501, "연결 오류"),
    MEMBER_EMAIL_EXIST(409, "이미 사용 중인 이메일 입니다."),
    MEMBER_USERNAME_EXIST(409, "이미 사용 중인 닉네임 입니다."),
    EMPTY_FILE(400, "빈 파일입니다."),
    IMAGE_SAVE_FAIL(400, "이메일 저장이 실패했습니다.");

    private final int code;
    private final String message;
}
