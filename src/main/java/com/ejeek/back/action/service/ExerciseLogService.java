package com.ejeek.back.action.service;

import com.ejeek.back.action.dto.ExerciseLogDto;
import com.ejeek.back.member.entity.Member;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public interface ExerciseLogService {

    ExerciseLogDto.Response createExerciseLog(ExerciseLogDto.CreateRequest request, Member member, MultipartFile multipartFile);

    ExerciseLogDto.Response getExerciseLogById(Long id, Member member);

    ExerciseLogDto.Response updateExerciseLog(Long id, ExerciseLogDto.UpdateRequest request, Member member, MultipartFile multipartFile);

    void deleteExerciseLog(Long id, Member member);
    List<ExerciseLogDto.Response> getAllExerciseLog(Member member, LocalDate date);
}
