package com.ejeek.back.controller;

import com.ejeek.back.dto.MemberDto;
import com.ejeek.back.member.Member;
import com.ejeek.back.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping(value = "/new")
    public String memberForm(Model model){
        model.addAttribute("MemberDto", new MemberDto());
        return "/signupForm";
    }

    @PostMapping(value="/new")
    public String memberForm(MemberDto memberDto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "/signupForm";
        }

        try {
            Member member = Member.createMember(memberDto, passwordEncoder);
            memberService.createMember(member);
        } catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "/signupForm";
        }

        return "redirect:/";
    }

    @GetMapping(value = "/login")
    public String loginMember() {
        return "/LoginForm";
    }

}