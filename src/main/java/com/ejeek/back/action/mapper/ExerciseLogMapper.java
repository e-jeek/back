package com.ejeek.back.action.mapper;

import com.ejeek.back.action.entity.ExerciseLog;
import com.ejeek.back.action.dto.ExerciseLogDto;
import com.ejeek.back.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface ExerciseLogMapper {
    @Mapping(target = "memberId", source = "member.id")
    @Mapping(target = "type", constant = "EXERCISE")
    @Mapping(target = "imageUrl", source = "exerciseLog.image.url")
    ExerciseLogDto.Response toResponse(ExerciseLog exerciseLog);


    @Mapping(source = "createRequest.name", target = "name")
    @Mapping(source = "createRequest.content", target = "content")
    ExerciseLog toEntity(ExerciseLogDto.CreateRequest createRequest, Member member);

    default ExerciseLogDto.CreateRequest fromRequestMap(Map<String, String> map) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(map.get("date"), formatter);

        return ExerciseLogDto.CreateRequest.builder()
                .date(date)
                .score(Integer.parseInt(map.get("score")))
                .content(map.get("content"))
                .name(map.get("name"))
                .duration(Integer.parseInt(map.get("duration")))
                .calories(Integer.parseInt(map.get("calories")))
                .build();
    }

    default ExerciseLogDto.UpdateRequest fromUpdateRequestMap(Map<String, String> map) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(map.get("date"), formatter);

        return ExerciseLogDto.UpdateRequest.builder()
                .date(date)
                .score(Integer.parseInt(map.get("score")))
                .content(map.get("content"))
                .name(map.get("name"))
                .duration(Integer.parseInt(map.get("duration")))
                .calories(Integer.parseInt(map.get("calories")))
                .build();
    }
}