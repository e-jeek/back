package com.ejeek.back.action.mapper;

import com.ejeek.back.action.entity.DietLog;
import com.ejeek.back.action.dto.DietLogDto;
import com.ejeek.back.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DietLogMapper {
    @Mapping(target = "memberId", source = "member.id")
    @Mapping(target = "type", constant = "DIET")
    @Mapping(target = "imageUrl", source = "dietLog.image.url")
    DietLogDto.Response toResponse(DietLog dietLog);

    @Mapping(target = "member", ignore = true)
    @Mapping(source = "createRequest.content", target = "content")
    DietLog toEntity(DietLogDto.CreateRequest createRequest, Member member);

}