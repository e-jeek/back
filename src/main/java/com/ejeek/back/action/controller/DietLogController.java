package com.ejeek.back.action.controller;

import com.ejeek.back.action.dto.DietLogDto;
import com.ejeek.back.action.service.DietLogService;
import com.ejeek.back.global.utils.UriCreator;
import com.ejeek.back.member.entity.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/action/diet")
@RequiredArgsConstructor
public class DietLogController {

    private final DietLogService dietLogService;

    @PostMapping
    public ResponseEntity<?> createDietLog(
            @RequestBody @Valid DietLogDto.CreateRequest requestDto,
            @AuthenticationPrincipal Member member) {

        DietLogDto.Response response = dietLogService.createDietLog(requestDto, member);
        return ResponseEntity.created(UriCreator.createURI(response.getId())).body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateDietLog(
            @PathVariable Long id,
            @RequestBody @Valid DietLogDto.UpdateRequest requestDto,
            @AuthenticationPrincipal Member member) {

        DietLogDto.Response response = dietLogService.updateDietLog(id, requestDto, member);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDietLog(
            @PathVariable Long id,
            @AuthenticationPrincipal Member member) {

        dietLogService.deleteDietLog(id, member);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<DietLogDto.Response>> getDietLogs(@AuthenticationPrincipal Member member) {

        List<DietLogDto.Response> logs = dietLogService.getAllDietLog(member);
        return ResponseEntity.ok(logs);
    }
}
