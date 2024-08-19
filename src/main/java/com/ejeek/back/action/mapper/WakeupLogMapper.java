package com.ejeek.back.action.mapper;

import com.ejeek.back.action.entity.WakeupLog;
import com.ejeek.back.action.dto.WakeupLogDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface WakeupLogMapper {
    @Mapping(target = "memberId", source = "member.id")
    @Mapping(target = "type", constant = "WAKEUP")
    WakeupLogDto.Response toResponse(WakeupLog wakeupLog);

    @Mapping(target = "member", ignore = true)
    WakeupLog toEntity(WakeupLogDto.CreateRequest createRequest);

    @Mapping(target = "member", ignore = true)
    void updateFromDto(WakeupLogDto.UpdateRequest updateRequest, @MappingTarget WakeupLog wakeupLog);
}