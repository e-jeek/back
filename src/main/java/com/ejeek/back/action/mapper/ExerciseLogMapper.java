package com.ejeek.back.action.mapper;

import com.ejeek.back.action.entity.ExerciseLog;
import com.ejeek.back.action.dto.ExerciseLogDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ExerciseLogMapper {
    @Mapping(target = "memberId", source = "member.id")
    @Mapping(target = "type", constant = "EXERCISE")
    ExerciseLogDto.Response toResponse(ExerciseLog exerciseLog);


    @Mapping(target = "member", ignore = true)
    ExerciseLog toEntity(ExerciseLogDto.CreateRequest createRequest);

    @Mapping(target = "member", ignore = true)
    void updateFromDto(ExerciseLogDto.UpdateRequest updateRequest, @MappingTarget ExerciseLog exerciseLog);
}