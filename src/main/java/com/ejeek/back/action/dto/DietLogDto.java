package com.ejeek.back.action.dto;

import com.ejeek.back.enums.DietType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class DietLogDto {

    @Getter
    @NoArgsConstructor
    public static class CreateRequest {
        private LocalDate date;
        private Integer score;
        private String content;
        private DietType dietType;
        private String foodName;
        private Integer calories;
    }

    @Getter
    @NoArgsConstructor
    public static class UpdateRequest {
        private LocalDate date;
        private Integer score;
        private String content;
        private DietType dietType;
        private String foodName;
        private Integer calories;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private LocalDate date;
        private Long memberId;
        private String type = "DIET";
        private Integer score;
        private String content;
        private DietType dietType;
        private String foodName;
        private Integer calories;
        private String imageUrl;
        private LocalDateTime createdAt;
        private LocalDateTime updateAt;
    }
}
