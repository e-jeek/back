package com.ejeek.back.action.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class WakeupLogDto {

    @Getter
    @AllArgsConstructor
    @Builder
    public static class CreateRequest {
        private LocalDate date;
        private Integer score;
        private LocalTime wakeupTime;
        private String content;
    }

    @Getter
    @AllArgsConstructor
    @Builder
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
        private String imageUrl;
        private LocalDateTime createdAt;
        private LocalDateTime updateAt;
    }
}
