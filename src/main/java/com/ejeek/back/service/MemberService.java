package com.ejeek.back.service;

import com.ejeek.back.member.Member;
import com.ejeek.back.dto.MemberDto;
import com.ejeek.back.mapper.MemberMapper;
import com.ejeek.back.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public MemberDto createMember(MemberDto memberDTO) {
        Member member = MemberMapper.toEntity(memberDTO);
        Member savedMember = memberRepository.save(member);
        return MemberMapper.toDTO(savedMember);
    }
}
