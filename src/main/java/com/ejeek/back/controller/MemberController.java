package com.ejeek.back.controller;

import com.ejeek.back.dto.MemberDto;
import com.ejeek.back.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<MemberDto> createMember(@RequestBody MemberDto memberDTO) {
        MemberDto createdMember = memberService.createMember(memberDTO);
        return ResponseEntity.ok(createdMember);
    }
}