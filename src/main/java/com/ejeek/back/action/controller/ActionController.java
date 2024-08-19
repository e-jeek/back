package com.ejeek.back.action.controller;

import com.ejeek.back.action.dto.DietLogDto;
import com.ejeek.back.action.dto.ExerciseLogDto;
import com.ejeek.back.action.dto.WakeupLogDto;
import com.ejeek.back.action.service.DietLogService;
import com.ejeek.back.action.service.ExerciseLogService;
import com.ejeek.back.action.service.WakeupLogService;
import com.ejeek.back.member.Member;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ActionController {

    private final ExerciseLogService exerciseLogService;
    private final DietLogService dietLogService;
    private final WakeupLogService wakeupLogService;

    @PostMapping("/action")
    public ResponseEntity<?> createLog(
            @RequestParam("type") String type,
            @RequestBody @Valid Object requestDto,
            @AuthenticationPrincipal Member member) {

        try {
            switch (type.toUpperCase()) {
                case "EXERCISE":
                    ExerciseLogDto.CreateRequest exerciseRequest = (ExerciseLogDto.CreateRequest) requestDto;
                    ExerciseLogDto.Response exerciseResponse = exerciseLogService.createExerciseLog(exerciseRequest, member);
                    return ResponseEntity.status(HttpStatus.CREATED).body(exerciseResponse);

                case "DIET":
                    DietLogDto.CreateRequest dietRequest = (DietLogDto.CreateRequest) requestDto;
                    DietLogDto.Response dietResponse = dietLogService.createDietLog(dietRequest, member);
                    return ResponseEntity.status(HttpStatus.CREATED).body(dietResponse);

                case "WAKEUP":
                    WakeupLogDto.CreateRequest wakeupRequest = (WakeupLogDto.CreateRequest) requestDto;
                    WakeupLogDto.Response wakeupResponse = wakeupLogService.createWakeupLog(wakeupRequest, member);
                    return ResponseEntity.status(HttpStatus.CREATED).body(wakeupResponse);

                default:
                    return ResponseEntity.badRequest().body("Invalid type");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to parse request data");
        }
    }

    @PutMapping("/action/{id}")
    public ResponseEntity<?> updateLog(
            @PathVariable Long id,
            @RequestParam("type") String type,
            @RequestBody @Valid Object requestDto,
            @AuthenticationPrincipal Member member) {

        try {
            switch (type.toUpperCase()) {
                case "EXERCISE":
                    ExerciseLogDto.UpdateRequest exerciseRequest = (ExerciseLogDto.UpdateRequest) requestDto;
                    ExerciseLogDto.Response exerciseResponse = exerciseLogService.updateExerciseLog(id, exerciseRequest, member);
                    return ResponseEntity.ok(exerciseResponse);

                case "DIET":
                    DietLogDto.UpdateRequest dietRequest = (DietLogDto.UpdateRequest) requestDto;
                    DietLogDto.Response dietResponse = dietLogService.updateDietLog(id, dietRequest, member);
                    return ResponseEntity.ok(dietResponse);

                case "WAKEUP":
                    WakeupLogDto.UpdateRequest wakeupRequest = (WakeupLogDto.UpdateRequest) requestDto;
                    WakeupLogDto.Response wakeupResponse = wakeupLogService.updateWakeupLog(id, wakeupRequest, member);
                    return ResponseEntity.ok(wakeupResponse);

                default:
                    return ResponseEntity.badRequest().body("Invalid type");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to parse request data");
        }
    }

    @DeleteMapping("/action/{id}")
    public ResponseEntity<?> deleteLog(
            @PathVariable Long id,
            @RequestParam("type") String type,
            @AuthenticationPrincipal Member member) throws EntityNotFoundException {

        try {
            switch (type.toUpperCase()) {
                case "EXERCISE":
                    exerciseLogService.deleteExerciseLog(id, member);
                    return ResponseEntity.noContent().build();

                case "DIET":
                    dietLogService.deleteDietLog(id, member);
                    return ResponseEntity.noContent().build();

                case "WAKEUP":
                    wakeupLogService.deleteWakeupLog(id, member);
                    return ResponseEntity.noContent().build();

                default:
                    return ResponseEntity.badRequest().body("Invalid type");
            }
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @GetMapping("/actions/exercise")
    public ResponseEntity<List<ExerciseLogDto.Response>> getExerciseLogs(@AuthenticationPrincipal Member member) {
        List<ExerciseLogDto.Response> logs = exerciseLogService.getAllExerciseLog(member);
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/actions/diet")
    public ResponseEntity<List<DietLogDto.Response>> getDietLogs(@AuthenticationPrincipal Member member) {
        List<DietLogDto.Response> logs = dietLogService.getAllDietLog(member);
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/actions/wakeup")
    public ResponseEntity<List<WakeupLogDto.Response>> getWakeupLogs(@AuthenticationPrincipal Member member) {
        List<WakeupLogDto.Response> logs = wakeupLogService.getAllWakeupLog(member);
        return ResponseEntity.ok(logs);
    }
}
