package com.ejeek.back.member.controller;


import com.ejeek.back.member.dto.MemberDto;
import com.ejeek.back.member.entity.Member;
import com.ejeek.back.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<?> createMember(@Valid @RequestBody MemberDto memberDTO) {
        Member createdMember = memberService.createMember(memberDTO);
        return ResponseEntity.ok(createdMember);
    }

}