package com.ejeek.back.action.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ExerciseLogDto {

    @Getter
    @NoArgsConstructor
    public static class CreateRequest {
        private LocalDate date;
        private Integer score;
        private String content;
        private String name;
        private Integer duration;
        private Integer calories;
    }

    @Getter
    @NoArgsConstructor
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
        private LocalDateTime createdAt;
        private LocalDateTime updateAt;
    }
}
