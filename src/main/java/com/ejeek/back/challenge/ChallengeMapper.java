package com.ejeek.back.challenge;

import com.ejeek.back.challenge.challenge_confirm.ChallengeConfirm;
import com.ejeek.back.challenge.challenge_confirm.ChallengeConfirmDto;
import com.ejeek.back.challenge.challenge_member.ChallengeMember;
import com.ejeek.back.challenge.challenge_member.ChallengeMemberDto;
import com.ejeek.back.hashtag.Hashtag;
import com.ejeek.back.member.Member;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChallengeMapper {

    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "content", source = "request.content")
    Challenge toChallenge(ChallengeDto.Request request, Member member);


    @Named("E2R")
    @Mapping(target = "id", source = "challenge.id")
    @Mapping(target = "member.id", source = "challenge.member.id")
    @Mapping(target = "member.nickname", source = "challenge.member.nickname")
    @Mapping(target = "imageUrl", source = "challenge.image.url")
    ChallengeDto.Response toChallengeResponse(Challenge challenge);

    @IterableMapping(qualifiedByName = "E2R")
    List<ChallengeDto.Response> toChallengeResponseList(List<Challenge> challenges);

    @Mapping(target = "member", source = "member")
    @Mapping(target = "challenge", source = "challenge")
    ChallengeMember toChallengeMember(Challenge challenge, Member member);

    @Mapping(target = "memberId", source = "challengeMember.member.id")
    @Mapping(target = "challengeId", source = "challengeMember.challenge.id")
    ChallengeMemberDto.Response toChallengeMemberResponse(ChallengeMember challengeMember);

    @Mapping(target = "content", source = "request.content")
    @Mapping(target = "member", source = "member")
    @Mapping(target = "challenge", source = "challenge")
    ChallengeConfirm toChallengeConfirm(ChallengeConfirmDto.Request request, Member member, Challenge challenge);

    @Mapping(target = "memberId", source = "confirm.member.id")
    @Mapping(target = "challengeId", source = "confirm.challenge.id")
    @Mapping(target = "imageUrl", source = "confirm.image.url")
    ChallengeConfirmDto.Response toChallengeConfirmResponse(ChallengeConfirm confirm);

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
