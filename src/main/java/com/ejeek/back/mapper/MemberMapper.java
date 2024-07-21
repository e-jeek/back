package com.ejeek.back.mapper;

import com.ejeek.back.member.Member;
import com.ejeek.back.dto.MemberDto;

public class MemberMapper {

    public static MemberDto toDTO(Member member) {
        MemberDto dto = new MemberDto();
        dto.setId(member.getId());
        dto.setEmail(member.getEmail());
        dto.setPassword(member.getPassword());
        dto.setName(member.getName());
        dto.setNickname(member.getNickname());
        dto.setStatus(member.getStatus());
        dto.setContent(member.getContent());
        dto.setBirth(member.getBirth());
        dto.setGender(member.getGender());
        dto.setHeight(member.getHeight());
        dto.setWeight(member.getWeight());
        dto.setPolicy(member.getPolicy());
        dto.setMarketing(member.getMarketing());
        return dto;
    }

    public static Member toEntity(MemberDto dto) {
        Member member = new Member();
        member.setEmail(dto.getEmail());
        member.setPassword(dto.getPassword());
        member.setName(dto.getName());
        member.setNickname(dto.getNickname());
        member.setStatus(dto.getStatus());
        member.setContent(dto.getContent());
        member.setBirth(dto.getBirth());
        member.setGender(dto.getGender());
        member.setHeight(dto.getHeight());
        member.setWeight(dto.getWeight());
        member.setPolicy(dto.getPolicy());
        member.setMarketing(dto.getMarketing());
        return member;
    }
}