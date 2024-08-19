package com.ejeek.back.action.mapper;

import com.ejeek.back.action.entity.DietLog;
import com.ejeek.back.action.dto.DietLogDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DietLogMapper {
    @Mapping(target = "memberId", source = "member.id")
    @Mapping(target = "type", constant = "DIET")
    DietLogDto.Response toResponse(DietLog dietLog);

    @Mapping(target = "member", ignore = true)
    DietLog toEntity(DietLogDto.CreateRequest createRequest);

    @Mapping(target = "member", ignore = true)
    void updateFromDto(DietLogDto.UpdateRequest updateRequest, @MappingTarget DietLog dietLog);
}