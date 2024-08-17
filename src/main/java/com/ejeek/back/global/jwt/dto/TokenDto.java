package com.ejeek.back.global.jwt.dto;

import lombok.*;


public class TokenDto {
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private String accessToken;
        private String refreshToken;
    }
}

