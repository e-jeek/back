package com.ejeek.back.global.jwt.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {
    private String aceesToken;
    private String refreshToken;
}

