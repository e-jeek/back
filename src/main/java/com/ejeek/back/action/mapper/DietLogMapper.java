package com.ejeek.back.action.mapper;

import com.ejeek.back.action.entity.DietLog;
import com.ejeek.back.action.dto.DietLogDto;
import com.ejeek.back.enums.DietType;
import com.ejeek.back.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface DietLogMapper {
    @Mapping(target = "memberId", source = "member.id")
    @Mapping(target = "type", constant = "DIET")
    @Mapping(target = "imageUrl", source = "dietLog.image.url")
    DietLogDto.Response toResponse(DietLog dietLog);

    @Mapping(source = "createRequest.content", target = "content")
    DietLog toEntity(DietLogDto.CreateRequest createRequest, Member member);

    default DietLogDto.CreateRequest fromRequestMap(Map<String, String> map) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(map.get("date"), formatter);

        return DietLogDto.CreateRequest.builder()
                .date(date)
                .score(Integer.parseInt(map.get("score")))
                .content(map.get("content"))
                .dietType(DietType.valueOf(map.get("dietType")))
                .foodName(map.get("foodName"))
                .calories(Integer.parseInt(map.get("calories")))
                .build();
    }

    default DietLogDto.UpdateRequest fromUpdateRequestMap(Map<String, String> map) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(map.get("date"), formatter);

        return DietLogDto.UpdateRequest.builder()
                .date(date)
                .score(Integer.parseInt(map.get("score")))
                .content(map.get("content"))
                .dietType(DietType.valueOf(map.get("dietType")))
                .foodName(map.get("foodName"))
                .calories(Integer.parseInt(map.get("calories")))
                .build();
    }
}