package com.ejeek.back.comment.mapper;


import com.ejeek.back.comment.dto.CommentDto;
import com.ejeek.back.comment.entity.Comment;
import com.ejeek.back.feed.entity.Feed;
import com.ejeek.back.hashtag.Hashtag;
import com.ejeek.back.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

    @Mapping(target = "content", source = "request.content")
    Comment toCommentEntity(CommentDto.CommentRequest request, Member member, Feed feed);

    @Mapping(target = "content", source = "request.content")
    Comment toUpdateCommentEntity(CommentDto.CommentUpdateRequest request, Member member, Feed feed);

    @Mapping(target = "id", source = "comment.id")
    @Mapping(target = "content", source = "comment.content")
    CommentDto.CommentResponse toCommentDto(Comment comment);


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
