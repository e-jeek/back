package com.ejeek.back.action.service;

import com.ejeek.back.action.dto.WakeupLogDto;
import com.ejeek.back.member.entity.Member;

import java.util.List;

public interface WakeupLogService {

    WakeupLogDto.Response createWakeupLog(WakeupLogDto.CreateRequest request, Member member);

    WakeupLogDto.Response updateWakeupLog(Long id, WakeupLogDto.UpdateRequest request, Member member);


    void deleteWakeupLog(Long id, Member member);

    List<WakeupLogDto.Response> getAllWakeupLog(Member member);
}
