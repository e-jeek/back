package com.ejeek.back.member.controller;


import com.ejeek.back.global.jwt.dto.TokenDto;
import com.ejeek.back.global.utils.UriCreator;
import com.ejeek.back.member.dto.MemberDto;
import com.ejeek.back.member.entity.Member;
import com.ejeek.back.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<?> createMember(@Valid @RequestBody MemberDto.SignupRequest request) {
        MemberDto.SimpleResponse createdMember = memberService.createMember(request);

        return ResponseEntity.created(UriCreator.createURI(createdMember.getId())).body(createdMember);
    }

    @GetMapping("/email")
    public ResponseEntity<?> checkEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(memberService.checkEmail(email));
    }

    @GetMapping("/nickname")
    public ResponseEntity<?> checkNickname(@RequestParam("nickname") String nickname) {
        return ResponseEntity.ok(memberService.checkNickname(nickname));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authorize(@Valid @RequestBody MemberDto.LoginRequest request) {

        TokenDto.Response response = memberService.loginMember(request);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("ACCESS_TOKEN", "Bearer " + response.getAccessToken());
        httpHeaders.add("REFRESH_TOKEN", "Bearer " + response.getRefreshToken());

        return new ResponseEntity<>("ok", httpHeaders, HttpStatus.OK);
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<?> deleteMember(@AuthenticationPrincipal Member member, @PathVariable(value = "Id") Long id) {
        memberService.deleteMember(id, member);

        return ResponseEntity.noContent().build();
    }
}