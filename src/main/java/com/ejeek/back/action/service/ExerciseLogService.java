package com.ejeek.back.action.service;

import com.ejeek.back.action.dto.ExerciseLogDto;
import com.ejeek.back.member.entity.Member;

import java.util.List;

public interface ExerciseLogService {

    ExerciseLogDto.Response createExerciseLog(ExerciseLogDto.CreateRequest request, Member member);

    ExerciseLogDto.Response getExerciseLogById(Long id, Member member);

    ExerciseLogDto.Response updateExerciseLog(Long id, ExerciseLogDto.UpdateRequest request, Member member);

    void deleteExerciseLog(Long id, Member member);
    List<ExerciseLogDto.Response> getAllExerciseLog(Member member);
}
