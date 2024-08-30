package com.ejeek.back.member.service;


import com.ejeek.back.global.exception.CustomException;
import com.ejeek.back.global.exception.ExceptionCode;
import com.ejeek.back.global.jwt.dto.TokenDto;
import com.ejeek.back.global.jwt.provider.TokenProvider;
import com.ejeek.back.member.MemberMapper;
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

    private final MemberMapper memberMapper;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public MemberDto.SimpleResponse createMember(MemberDto.SignupRequest request) {
        validateDuplicateMember(request.getEmail());
        Member member = memberMapper.toEntity(request);


        member.updateEncryptedPassword(passwordEncoder.encode(member.getPassword()));
        member.updateStatus();
        member.updateRole();



        return memberMapper.memberToSimpleResponseDto(memberRepository.save(member));
    }

    private void validateDuplicateMember(String email){
        Optional<Member> findMember = memberRepository.findByEmail(email);

        if(findMember.isPresent()){
            throw new CustomException(ExceptionCode.MEMBER_EMAIL_EXIST);
        }
    }

    public TokenDto.Response loginMember(MemberDto.LoginRequest request) {
        Member member = getMemberByEmail(request.getEmail());

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())){
            throw new CustomException(ExceptionCode.MEMBER_PASSWORD_INCORRECT);
        }

        return tokenProvider.generateToken(memberMapper.memberToSimpleResponseDto(member));
    }

    public Member getMemberByEmail(String email){

        return memberRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(ExceptionCode.MEMBER_NOT_EXIST));
    }

}
