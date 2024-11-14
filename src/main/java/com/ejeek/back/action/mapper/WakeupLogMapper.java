package com.ejeek.back.action.mapper;

import com.ejeek.back.action.entity.WakeupLog;
import com.ejeek.back.action.dto.WakeupLogDto;
import com.ejeek.back.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface WakeupLogMapper {
    @Mapping(target = "memberId", source = "member.id")
    @Mapping(target = "type", constant = "WAKEUP")
    @Mapping(target = "imageUrl", source = "wakeupLog.image.url")
    WakeupLogDto.Response toResponse(WakeupLog wakeupLog);

    @Mapping(source = "createRequest.content", target = "content")
    @Mapping(source = "createRequest.wakeupTime", target = "wakeupTime")
    WakeupLog toEntity(WakeupLogDto.CreateRequest createRequest, Member member);

    default WakeupLogDto.CreateRequest fromRequestMap(Map<String, String> map) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(map.get("date"), formatter);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime wakeupTime = LocalTime.parse(map.get("wakeupTime"), timeFormatter);

        return WakeupLogDto.CreateRequest.builder()
                .date(date)
                .score(Integer.parseInt(map.get("score")))
                .content(map.get("content"))
                .wakeupTime(wakeupTime)
                .build();
    }

    default WakeupLogDto.UpdateRequest fromUpdateRequestMap(Map<String, String> map) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(map.get("date"), formatter);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime wakeupTime = LocalTime.parse(map.get("wakeupTime"), timeFormatter);

        return WakeupLogDto.UpdateRequest.builder()
                .date(date)
                .score(Integer.parseInt(map.get("score")))
                .content(map.get("content"))
                .wakeupTime(wakeupTime)
                .build();
    }
}