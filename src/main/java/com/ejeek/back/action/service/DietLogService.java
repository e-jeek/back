package com.ejeek.back.action.service;

import com.ejeek.back.action.dto.DietLogDto;
import com.ejeek.back.member.entity.Member;

import java.util.List;

public interface DietLogService {

    DietLogDto.Response createDietLog(DietLogDto.CreateRequest request, Member member);

    DietLogDto.Response getDietLogById(Long id, Member member);

    DietLogDto.Response updateDietLog(Long id, DietLogDto.UpdateRequest request, Member member);

    void deleteDietLog(Long id, Member member);

    List<DietLogDto.Response> getAllDietLog(Member member);
}
