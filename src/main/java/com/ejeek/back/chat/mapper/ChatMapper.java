package com.ejeek.back.chat.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Slice;

@Mapper(componentModel = "spring")
public interface ChatMapper {

//    @Named("E2R")
//    @Mapping(target = "chatId", source = "id")
//    @Mapping(target = "memberId", source = "member.id")
//    @Mapping(target = "nickname", source = "member.nickname")
//    ChatDto.Response toResponse(Chat chat);

//    @IterableMapping(qualifiedByName = "E2R")
//    Slice<ChatDto.Response> toSliceResponse(Slice<Chat> chattingList);
//
//    @Mapping(target = "challenge.id", source = "challengeId")
//    @Mapping(target = "member.id", source = "request.memberId")
//    Chat toChat(ChatDto.Request request, Long challengeId);
}
