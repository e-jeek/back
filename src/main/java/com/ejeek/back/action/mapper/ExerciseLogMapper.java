package com.ejeek.back.action.mapper;

import com.ejeek.back.action.entity.ExerciseLog;
import com.ejeek.back.action.dto.ExerciseLogDto;
import com.ejeek.back.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ExerciseLogMapper {
    @Mapping(target = "memberId", source = "member.id")
    @Mapping(target = "type", constant = "EXERCISE")
    @Mapping(target = "imageUrl", source = "exerciseLog.image.url")
    ExerciseLogDto.Response toResponse(ExerciseLog exerciseLog);

    @Mapping(source = "createRequest.name", target = "name")
    @Mapping(source = "createRequest.content", target = "content")
    ExerciseLog toEntity(ExerciseLogDto.CreateRequest createRequest, Member member);

}