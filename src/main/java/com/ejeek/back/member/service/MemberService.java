package com.ejeek.back.member.service;

import com.ejeek.back.global.exception.CustomException;
import com.ejeek.back.global.exception.ExceptionCode;
import com.ejeek.back.global.jwt.dto.TokenDto;
import com.ejeek.back.global.jwt.provider.TokenProvider;
import com.ejeek.back.member.dto.LoginDto;
import com.ejeek.back.member.dto.MemberDto;
import com.ejeek.back.member.entity.Member;
import com.ejeek.back.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public Member createMember(MemberDto memberDto) {
        validateDuplicateMember(memberDto.getEmail());
        Member member = Member.createMember(memberDto);
        member.setEncryptedPassword(passwordEncoder.encode(member.getPassword()));

        return memberRepository.save(member);
    }


    private void validateDuplicateMember(String email){
        Optional<Member> findMember = memberRepository.findByEmail(email);
        if(findMember.isPresent()){
            throw new CustomException(ExceptionCode.MEMBER_EMAIL_EXIST);
        }
    }

    public TokenDto loginMember(LoginDto loginDto) {
        Member member = getMemberByEmail(loginDto.getEmail());

        if (!passwordEncoder.matches(loginDto.getPassword(), member.getPassword())){
            throw new CustomException(ExceptionCode.MEMBER_PASSWORD_INCORRECT);
        }

        return tokenProvider.generateToken(member);
    }

    private Member getMemberByEmail(String email){

        return memberRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(ExceptionCode.MEMBER_NOT_EXIST));
    }

}
