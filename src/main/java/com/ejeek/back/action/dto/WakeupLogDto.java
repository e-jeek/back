package com.ejeek.back.action.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class WakeupLogDto {

    @Getter
    @NoArgsConstructor
    public static class CreateRequest {
        private Integer score;
        private LocalDateTime wakeupTime;
        private String content;
    }

    @Getter
    @NoArgsConstructor
    public static class UpdateRequest {
        private Integer score;
        private LocalDateTime wakeupTime;
        private String content;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private Long memberId;
        private String type = "WAKEUP";
        private Integer score;
        private String content;
        private LocalDateTime wakeupTime;
        private LocalDateTime createdAt;
        private LocalDateTime updateAt;
    }
}
