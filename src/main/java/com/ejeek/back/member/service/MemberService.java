package com.ejeek.back.member.service;


import com.ejeek.back.enums.MemberStatus;
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
        Member member = memberMapper.toEntity(request);

        member.updateEncryptedPassword(passwordEncoder.encode(member.getPassword()));
        member.updateStatus(MemberStatus.ACTIVE);
        member.updateRole();

        return memberMapper.memberToSimpleResponseDto(memberRepository.save(member));
    }

    public String checkEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);

        if (member.isPresent()) {
            throw new CustomException(ExceptionCode.MEMBER_EMAIL_EXIST);
        }

        return "사용 가능한 이메일 입니다.";
    }

    public String checkNickname(String nickname) {
        Optional<Member> member = memberRepository.findByNickname(nickname);

        if (member.isPresent()) {
            throw new CustomException(ExceptionCode.MEMBER_EMAIL_EXIST);
        }

        return "사용 가능한 닉네임 입니다.";
    }

    public TokenDto.Response loginMember(MemberDto.LoginRequest request) {
        Member member = getMemberByEmail(request.getEmail());

        if(member.getStatus().equals(MemberStatus.DELETE)) {
            throw new CustomException(ExceptionCode.DELETE_MEMBER);
        }
        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())){
            throw new CustomException(ExceptionCode.MEMBER_PASSWORD_INCORRECT);
        }

        return tokenProvider.generateToken(memberMapper.memberToSimpleResponseDto(member));
    }

    public Member getMemberByEmail(String email){

        return memberRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(ExceptionCode.MEMBER_NOT_EXIST));
    }

    public void deleteMember(Long id, Member member){
        Member findMember = getMemberById(id);

        verifyMember(member, findMember);

        member.updateStatus(MemberStatus.DELETE);

        memberRepository.save(member);
    }

    public Member getMemberById(Long id){
        return memberRepository.findById(id).orElseThrow(
                () -> new CustomException(ExceptionCode.MEMBER_NOT_EXIST));
    }

    public void verifyMember(Member member, Member findMember) {
        if (!member.getEmail().equals(findMember.getEmail())) {
            throw new CustomException(ExceptionCode.MEMBER_NOT_SAME);
        }
    }

}
