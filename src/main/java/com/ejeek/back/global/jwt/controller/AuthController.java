package com.ejeek.back.global.jwt.controller;

import com.ejeek.back.global.jwt.dto.TokenDto;
import com.ejeek.back.member.dto.LoginDto;
import com.ejeek.back.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
//사용자 인가 체크 및 token 발급 controller
public class AuthController {

    private final MemberService memberService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authorize(@Valid @RequestBody LoginDto loginDto) {

        TokenDto response = memberService.loginMember(loginDto);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("ACCESS_TOKEN", "Bearer " + response.getAccessToken());
        httpHeaders.add("REFRESH_TOKEN", "Bearer " + response.getRefreshToken());

        return new ResponseEntity<>("ok", httpHeaders, HttpStatus.OK);
    }
}