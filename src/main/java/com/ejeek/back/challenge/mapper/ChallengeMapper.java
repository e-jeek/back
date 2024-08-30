package com.ejeek.back.challenge.mapper;

import com.ejeek.back.challenge.dto.ChallengeDto;
import com.ejeek.back.challenge.entity.Challenge;
import com.ejeek.back.challenge.entity.ChallengeConfirm;
import com.ejeek.back.challenge.dto.ChallengeConfirmDto;
import com.ejeek.back.challenge.entity.ChallengeMember;
import com.ejeek.back.challenge.dto.ChallengeMemberDto;
import com.ejeek.back.hashtag.Hashtag;
import com.ejeek.back.member.entity.Member;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChallengeMapper {

    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "content", source = "request.content")
    Challenge toChallenge(ChallengeDto.Request request, Member member);

    @Named("CE2CR")
    @Mapping(target = "id", source = "challenge.id")
    @Mapping(target = "member.id", source = "challenge.member.id")
    @Mapping(target = "member.nickname", source = "challenge.member.nickname")
    @Mapping(target = "imageUrl", source = "challenge.image.url")
    ChallengeDto.Response toChallengeResponse(Challenge challenge);

    @IterableMapping(qualifiedByName = "CE2CR")
    List<ChallengeDto.Response> toChallengeResponseList(List<Challenge> challenges);

    @Mapping(target = "member", source = "member")
    @Mapping(target = "challenge", source = "challenge")
    ChallengeMember toChallengeMember(Challenge challenge, Member member);

    @Named("CME2CMR")
    @Mapping(target = "memberId", source = "challengeMember.member.id")
    @Mapping(target = "challengeId", source = "challengeMember.challenge.id")
    ChallengeMemberDto.Response toChallengeMemberResponse(ChallengeMember challengeMember);

    @IterableMapping(qualifiedByName = "CME2CMR")
    List<ChallengeMemberDto.Response> toChallengeMemberResponseList(List<ChallengeMember> challengeMembers);

    @Mapping(target = "content", source = "request.content")
    @Mapping(target = "member", source = "member")
    @Mapping(target = "challenge", source = "challenge")
    ChallengeConfirm toChallengeConfirm(ChallengeConfirmDto.Request request, Member member, Challenge challenge);

    @Named("CCE2CCR")
    @Mapping(target = "memberId", source = "confirm.member.id")
    @Mapping(target = "challengeId", source = "confirm.challenge.id")
    @Mapping(target = "imageUrl", source = "confirm.image.url")
    ChallengeConfirmDto.Response toChallengeConfirmResponse(ChallengeConfirm confirm);

    @IterableMapping(qualifiedByName = "CCE2CCR")
    List<ChallengeConfirmDto.Response> toChallengeConfirmResponseList(List<ChallengeConfirm> challengeConfirms);
    default List<String> map(List<Hashtag> hashtags) {
        if (hashtags == null) {
            return null;
        }
        List<String> hashtagDtos = new ArrayList<>();
        for (Hashtag hashtag : hashtags) {
            hashtagDtos.add(hashtag.getTagName());
        }
        return hashtagDtos;
    }
}
