package com.ejeek.back.enums;

import lombok.Getter;

@Getter
public enum MemberStatus {

    ACTIVE("활성화"),
    DISABLED("비활성화"),
    DELETE("삭제");

    private final String status;

    MemberStatus(String status) {
        this.status = status;
    }
}
