package com.ejeek.back.challenge;

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
    ChallengeDto.Response toResponse(Challenge challenge);

    @IterableMapping(qualifiedByName = "E2R")
    List<ChallengeDto.Response> toReponseList(List<Challenge> challenges);

    default List<String> map(List<Hashtag> hashtags) {
        if (hashtags == null) {
            return null;
        }
        List<String> hashtagDtos = new ArrayList<>();
        for (Hashtag hashtag : hashtags) {
            hashtagDtos.add(hashtag.getReference().getTagName());
        }
        return hashtagDtos;
    }
}
