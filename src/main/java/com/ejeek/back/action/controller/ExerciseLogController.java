package com.ejeek.back.action.controller;

import com.ejeek.back.action.dto.ExerciseLogDto;
import com.ejeek.back.action.service.ExerciseLogService;
import com.ejeek.back.global.utils.UriCreator;
import com.ejeek.back.member.entity.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/action/exercise")
@RequiredArgsConstructor
public class ExerciseLogController {

    private final ExerciseLogService exerciseLogService;

    @PostMapping
    public ResponseEntity<?> createExerciseLog(
            @RequestBody @Valid ExerciseLogDto.CreateRequest requestDto,
            @AuthenticationPrincipal Member member) {

        ExerciseLogDto.Response response = exerciseLogService.createExerciseLog(requestDto, member);
        return ResponseEntity.created(UriCreator.createURI(response.getId())).body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateExerciseLog(
            @PathVariable Long id,
            @RequestBody @Valid ExerciseLogDto.UpdateRequest requestDto,
            @AuthenticationPrincipal Member member) {

        ExerciseLogDto.Response response = exerciseLogService.updateExerciseLog(id, requestDto, member);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExerciseLog(
            @PathVariable Long id,
            @AuthenticationPrincipal Member member) {

        exerciseLogService.deleteExerciseLog(id, member);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ExerciseLogDto.Response>> getExerciseLogs(@AuthenticationPrincipal Member member) {

        List<ExerciseLogDto.Response> logs = exerciseLogService.getAllExerciseLog(member);
        return ResponseEntity.ok(logs);
    }
}
