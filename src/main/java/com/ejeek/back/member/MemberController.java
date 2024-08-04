package com.ejeek.back.member;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/nickname")
    public ResponseEntity<?> checkNickname(@RequestParam("nickname") String nickname) {

        return ResponseEntity.ok(memberService.checkNickname(nickname));
    }

    @GetMapping("/email")
    public ResponseEntity<?> checkEmail(@RequestParam("email") String email) {

        return ResponseEntity.ok(memberService.checkNickname(email));
    }

}
