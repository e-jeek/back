package com.ejeek.back.challenge;

import com.ejeek.back.member.Member;
import com.ejeek.back.member.MemberDto;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Slice;

@Mapper(componentModel = "spring")
public interface ChallengeMapper {

    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "status", source = "request.status")
    @Mapping(target = "content", source = "request.content")
    Challenge toChallenge(ChallengeDto.Request request, Member member);


    @Named("E2R")
    @Mapping(target = "id", source = "challenge.id")
    @Mapping(target = "member", source = "member")
    ChallengeDto.Response toResponse(Challenge challenge, MemberDto.Response member);

    @IterableMapping(qualifiedByName = "E2R")
    Slice<ChallengeDto.Response> toReponseSlice(Slice<Challenge> challenges);
}
