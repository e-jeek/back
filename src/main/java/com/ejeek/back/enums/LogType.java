package com.ejeek.back.enums;

import lombok.Getter;

@Getter
public enum LogType {
    EXERCISE("exerciseLogServiceImpl"),
    DIET("dietLogServiceImpl"),
    WAKEUP("wakeupLogServiceImpl");

    private final String serviceName;

    LogType(String serviceName) {
        this.serviceName = serviceName;
    }

}
