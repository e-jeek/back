package com.ejeek.back.member;


import com.ejeek.back.member.dto.MemberDto;
import com.ejeek.back.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {

    Member toEntity(MemberDto.SignupRequest request);

    MemberDto.SimpleResponse memberToSimpleResponseDto(Member member);
}