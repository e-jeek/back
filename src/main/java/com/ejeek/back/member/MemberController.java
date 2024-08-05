package com.ejeek.back.member;

import com.amazonaws.services.kms.model.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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

    @GetMapping("/social/{provider}")
    public void signup(HttpServletRequest request, HttpServletResponse response,
                       @PathVariable String provider,
                       @RequestParam String type) throws IOException {

        // type 없을 시 에러
        if (type.isEmpty()) {
            throw new NotFoundException("type is empty");
        }


        // provider 없을 시 에러
        if (provider.isEmpty()) {
            throw new NotFoundException("provider is empty");
        }

        // 소셜 로그인 측에서 인증 후 Redirect됐을 때 회원가입인지 로그인인지 식별하기 위함
        // type: LOGIN, SIGNUP, VALIDATE(가입여부 확인)
        request.getSession().setAttribute("type", type.toUpperCase());

        // 소셜 로그인 화면 띄워주는 url
        response.sendRedirect("http://localhost:8080/oauth2/authorization/" + provider);
    }

}
