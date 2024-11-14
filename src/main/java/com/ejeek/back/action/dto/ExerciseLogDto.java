package com.ejeek.back.action.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ExerciseLogDto {

    @Getter
    @AllArgsConstructor
    @Builder
    public static class CreateRequest {
        private LocalDate date;
        private Integer score;
        private String content;
        private String name;
        private Integer duration;
        private Integer calories;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class UpdateRequest {
        private LocalDate date;
        private Integer score;
        private String content;
        private String name;
        private Integer duration;
        private Integer calories;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private LocalDate date;
        private Long memberId;
        private String type = "EXERCISE";
        private Integer score;
        private String content;
        private String name;
        private Integer duration;
        private Integer calories;
        private String imageUrl;
        private LocalDateTime createdAt;
        private LocalDateTime updateAt;
    }
}
