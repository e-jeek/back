package com.ejeek.back.action.controller;

import com.ejeek.back.action.dto.DietLogDto;
import com.ejeek.back.action.dto.ExerciseLogDto;
import com.ejeek.back.action.dto.WakeupLogDto;

import com.ejeek.back.action.service.LogService;
import com.ejeek.back.enums.LogType;
import com.ejeek.back.global.utils.UriCreator;
import com.ejeek.back.member.entity.Member;
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

    private final Map<String, LogService> logServiceMap;

    @PostMapping
    public ResponseEntity<?> createLog(
            @RequestParam("type") LogType type,
            @RequestPart @Valid Map<String, String> requestBody,
            @RequestPart(required = false) MultipartFile file,
            @AuthenticationPrincipal Member member) {

        LogService logService = logServiceMap.get(type.getServiceName());
        if (logService == null) {
            return ResponseEntity.badRequest().body("Invalid log type: " + type);
        }
        
        Object response = logService.createLog(requestBody, member, file);

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
            @RequestParam("type") LogType type,
            @RequestPart @Valid Map<String, String> requestBody,
            @RequestPart(required = false) MultipartFile file,
            @AuthenticationPrincipal Member member) {

        LogService logService = logServiceMap.get(type.getServiceName());
        Object response = logService.updateLog(id, requestBody, member, file);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLog(
            @PathVariable Long id,
            @RequestParam("type") LogType type,
            @AuthenticationPrincipal Member member) throws EntityNotFoundException {

        LogService logService = logServiceMap.get(type.getServiceName());
        logService.deleteLog(id, member);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/daily")
    public ResponseEntity<?> getDailyLogs(
            @AuthenticationPrincipal Member member,
            @RequestParam("type") LogType type,
            @RequestParam("date") LocalDate date) {

        LogService logService = logServiceMap.get(type.getServiceName());
        System.out.println("l");
        List<?> logs = logService.getAllLogs(member, date);

        System.out.println("k");
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLogById(
            @PathVariable Long id,
            @RequestParam("type") LogType type,
            @AuthenticationPrincipal Member member) {

        LogService logService = logServiceMap.get(type.getServiceName());
        Object response = logService.getLogById(id, member);

        return ResponseEntity.ok(response);

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

            for (LogType type : LogType.values()) {
                LogService logService = logServiceMap.get(type.getServiceName());
                if (logService != null) {
                    List<?> logs = logService.getAllLogs(member, date);
                    double averageScore = logService.getDailyAverageScore(member, date);

                    dailyLogs.put(type.name(), Map.of("average", averageScore, "data", logs));
                }
            }

            monthlyLogs.put(date.toString(), dailyLogs);
        }

        return ResponseEntity.ok(monthlyLogs);
    }


}
