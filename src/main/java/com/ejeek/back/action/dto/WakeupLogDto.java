package com.ejeek.back.action.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class WakeupLogDto {

    @Getter
    @NoArgsConstructor
    public static class CreateRequest {
        private LocalDate date;
        private Integer score;
        private LocalTime wakeupTime;
        private String content;
    }

    @Getter
    @NoArgsConstructor
    public static class UpdateRequest {
        private LocalDate date;
        private Integer score;
        private LocalTime wakeupTime;
        private String content;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private LocalDate date;
        private Long memberId;
        private String type = "WAKEUP";
        private Integer score;
        private String content;
        private LocalTime wakeupTime;
        private LocalDateTime createdAt;
        private LocalDateTime updateAt;
    }
}
