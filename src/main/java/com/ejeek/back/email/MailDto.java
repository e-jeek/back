package com.ejeek.back.email;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MailDto {

    private final String email;
    private final String code;
}
