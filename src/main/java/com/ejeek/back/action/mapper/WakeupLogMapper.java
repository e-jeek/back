package com.ejeek.back.action.mapper;

import com.ejeek.back.action.entity.WakeupLog;
import com.ejeek.back.action.dto.WakeupLogDto;
import com.ejeek.back.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface WakeupLogMapper {
    @Mapping(target = "memberId", source = "member.id")
    @Mapping(target = "type", constant = "WAKEUP")
    @Mapping(target = "imageUrl", source = "wakeupLog.image.url")
    WakeupLogDto.Response toResponse(WakeupLog wakeupLog);

    @Mapping(target = "member", ignore = true)
    @Mapping(source = "createRequest.content", target = "content")
    WakeupLog toEntity(WakeupLogDto.CreateRequest createRequest, Member member);

}