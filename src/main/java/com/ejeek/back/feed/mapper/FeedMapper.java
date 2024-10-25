package com.ejeek.back.feed.mapper;


import com.ejeek.back.feed.dto.FeedDto;
import com.ejeek.back.feed.entity.Feed;
import com.ejeek.back.hashtag.Hashtag;
import com.ejeek.back.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FeedMapper {

    @Mapping(target = "content", source = "request.content")
    Feed toFeedEntity(FeedDto.FeedRequest request, Member member);

    @Mapping(target = "content", source = "request.content")
    Feed toUpdateFeedEntity(FeedDto.FeedUpdateRequest request, Member member);



    @Mapping(target = "id", source = "feed.id")
    @Mapping(target = "content", source = "feed.content")
    FeedDto.FeedResponse toFeedDto(Feed feed);

    @Mapping(target = "id", source = "feed.id")
    @Mapping(target = "content", source = "feed.content")
    FeedDto.FeedResponse toFeedUpdateDto(Feed feed);




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