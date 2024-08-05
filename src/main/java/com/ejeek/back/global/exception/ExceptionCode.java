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
    IMAGE_SAVE_FAIL(400, "이메일 저장이 실패했습니다."),
    CHALLENGE_NOT_FOUND(404, "챌린지를 찾을 수 없습니다."),
    MEMBER_NOT_SAME(403, "권한이 없는 사용자 입니다."),
    PARTICIPANT_EXIST(400, "참가자가 존재하기 때문에 수정 및 삭제가 불가능 합니다."),
    CHALLENGE_CANNOT_MODIFY(400, "이미 시작하거나 끝난 챌린지이기 때문에 수정 및 삭제가 불가능 합니다."),
    CHALLENGE_DATE_ERROR(400, "현재보다 이후의 날짜로만 등록 가능합니다."),
    CHALLENGE_DUEDATE_ERROR(400, "마감일은 시작일보다 이전이어야 합니다."),
    CHALLENGE_STARTDATE_ERROR(400, "시작일은 종료일보다 이전이어야 합니다."),
    INVALID_SECRET_KEY(400, "비밀방은 비밀번호가 필요합니다.");

    private final int code;
    private final String message;
}
