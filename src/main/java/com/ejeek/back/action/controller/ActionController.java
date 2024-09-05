package com.ejeek.back.action.controller;

import com.ejeek.back.action.dto.DietLogDto;
import com.ejeek.back.action.dto.ExerciseLogDto;
import com.ejeek.back.action.dto.WakeupLogDto;
import com.ejeek.back.action.service.DietLogService;
import com.ejeek.back.action.service.ExerciseLogService;
import com.ejeek.back.action.service.WakeupLogService;
import com.ejeek.back.global.utils.UriCreator;
import com.ejeek.back.member.entity.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/actions")
@RequiredArgsConstructor
public class ActionController {

    private final ExerciseLogService exerciseLogService;
    private final DietLogService dietLogService;
    private final WakeupLogService wakeupLogService;

    @PostMapping
    public ResponseEntity<?> createLog(
            @RequestParam("type") String type,
            @RequestPart @Valid Map<String, Object> requestBody,
            @RequestPart(required = false) MultipartFile file,
            @AuthenticationPrincipal Member member) {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Object response;

        switch (type.toUpperCase()) {
            case "EXERCISE":
                ExerciseLogDto.CreateRequest exerciseRequest = objectMapper.convertValue(requestBody, ExerciseLogDto.CreateRequest.class);
                response = exerciseLogService.createExerciseLog(exerciseRequest, member, file);
                break;

            case "DIET":
                DietLogDto.CreateRequest dietRequest = objectMapper.convertValue(requestBody, DietLogDto.CreateRequest.class);
                response = dietLogService.createDietLog(dietRequest, member, file);
                break;

            case "WAKEUP":
                WakeupLogDto.CreateRequest wakeupRequest = objectMapper.convertValue(requestBody, WakeupLogDto.CreateRequest.class);
                response = wakeupLogService.createWakeupLog(wakeupRequest, member, file);
                break;

            default:
                return ResponseEntity.badRequest().body("Invalid type");
        }
        Long id;
        if (response instanceof ExerciseLogDto.Response) {
            id = ((ExerciseLogDto.Response) response).getId();
        } else if (response instanceof DietLogDto.Response) {
            id = ((DietLogDto.Response) response).getId();
        } else if (response instanceof WakeupLogDto.Response) {
            id = ((WakeupLogDto.Response) response).getId();
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unknown response type");
        }

        return ResponseEntity.created(UriCreator.createURI(id)).body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateLog(
            @PathVariable Long id,
            @RequestParam("type") String type,
            @RequestPart @Valid Map<String, Object> requestBody,
            @RequestPart(required = false) MultipartFile file,
            @AuthenticationPrincipal Member member) {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Object response;

        switch (type.toUpperCase()) {
            case "EXERCISE":
                ExerciseLogDto.UpdateRequest exerciseRequest = objectMapper.convertValue(requestBody, ExerciseLogDto.UpdateRequest.class);
                response = exerciseLogService.updateExerciseLog(id, exerciseRequest, member, file);
                break;

            case "DIET":
                DietLogDto.UpdateRequest dietRequest = objectMapper.convertValue(requestBody, DietLogDto.UpdateRequest.class);
                response = dietLogService.updateDietLog(id, dietRequest, member, file);
                break;

            case "WAKEUP":
                WakeupLogDto.UpdateRequest wakeupRequest = objectMapper.convertValue(requestBody, WakeupLogDto.UpdateRequest.class);
                response = wakeupLogService.updateWakeupLog(id, wakeupRequest, member, file);
                break;

            default:
                return ResponseEntity.badRequest().body("Invalid type");
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLog(
            @PathVariable Long id,
            @RequestParam("type") String type,
            @AuthenticationPrincipal Member member) throws EntityNotFoundException {

        switch (type.toUpperCase()) {
            case "EXERCISE":
                exerciseLogService.deleteExerciseLog(id, member);
                break;

            case "DIET":
                dietLogService.deleteDietLog(id, member);
                break;

            case "WAKEUP":
                wakeupLogService.deleteWakeupLog(id, member);
                break;

            default:
                return ResponseEntity.badRequest().body("Invalid type");
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exercise")
    public ResponseEntity<List<ExerciseLogDto.Response>> getDailyExerciseLogs(
            @AuthenticationPrincipal Member member,
            @RequestParam(value = "date") LocalDate date) {
        List<ExerciseLogDto.Response> logs = exerciseLogService.getAllExerciseLog(member, date);
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/diet")
    public ResponseEntity<List<DietLogDto.Response>> getDailyDietLogs(
            @AuthenticationPrincipal Member member,
            @RequestParam(value = "date") LocalDate date) {
        List<DietLogDto.Response> logs = dietLogService.getAllDietLog(member, date);
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/wakeup")
    public ResponseEntity<List<WakeupLogDto.Response>> getDailyWakeupLogs(
            @AuthenticationPrincipal Member member,
            @RequestParam(value = "date") LocalDate date) {
        List<WakeupLogDto.Response> logs = wakeupLogService.getAllWakeupLog(member, date);
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLogById(
            @PathVariable Long id,
            @RequestParam("type") String type,
            @AuthenticationPrincipal Member member) {

        switch (type.toUpperCase()) {
            case "EXERCISE":
                ExerciseLogDto.Response exerciseResponse = exerciseLogService.getExerciseLogById(id, member);
                return ResponseEntity.ok(exerciseResponse);

            case "DIET":
                DietLogDto.Response dietResponse = dietLogService.getDietLogById(id, member);
                return ResponseEntity.ok(dietResponse);

            case "WAKEUP":
                WakeupLogDto.Response wakeupResponse = wakeupLogService.getWakeupLogById(id, member);
                return ResponseEntity.ok(wakeupResponse);

            default:
                return ResponseEntity.badRequest().body("Invalid type");
        }
    }
    @GetMapping
    public ResponseEntity<Map<String, Map<String, Object>>> getMonthlyLogs(
            @RequestParam int year,
            @RequestParam int month,
            @AuthenticationPrincipal Member member) {

        Map<String, Map<String, Object>> monthlyLogs = new HashMap<>();

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            Map<String, Object> dailyLogs = new HashMap<>();

            List<WakeupLogDto.Response> wakeupLogs = wakeupLogService.getAllWakeupLog(member, date);
            List<DietLogDto.Response> dietLogs = dietLogService.getAllDietLog(member, date);
            List<ExerciseLogDto.Response> exerciseLogs = exerciseLogService.getAllExerciseLog(member, date);

            double wakeupAverage = wakeupLogs.stream().mapToDouble(WakeupLogDto.Response::getScore).average().orElse(0);
            double dietAverage = dietLogs.stream().mapToDouble(DietLogDto.Response::getScore).average().orElse(0);
            double exerciseAverage = exerciseLogs.stream().mapToDouble(ExerciseLogDto.Response::getScore).average().orElse(0);

            dailyLogs.put("WAKEUP", Map.of("average", wakeupAverage, "data", wakeupLogs));
            dailyLogs.put("DIET", Map.of("average", dietAverage, "data", dietLogs));
            dailyLogs.put("EXERCISE", Map.of("average", exerciseAverage, "data", exerciseLogs));

            monthlyLogs.put(date.toString(), dailyLogs);
        }

        return ResponseEntity.ok(monthlyLogs);
    }

}
