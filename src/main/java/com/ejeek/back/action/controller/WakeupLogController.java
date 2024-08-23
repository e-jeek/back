package com.ejeek.back.action.controller;

import com.ejeek.back.action.dto.WakeupLogDto;
import com.ejeek.back.action.service.WakeupLogService;
import com.ejeek.back.global.utils.UriCreator;
import com.ejeek.back.member.entity.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/action/wakeup")
@RequiredArgsConstructor
public class WakeupLogController {

    private final WakeupLogService wakeupLogService;

    @PostMapping
    public ResponseEntity<?> createWakeupLog(
            @RequestBody @Valid WakeupLogDto.CreateRequest requestDto,
            @AuthenticationPrincipal Member member) {

        WakeupLogDto.Response response = wakeupLogService.createWakeupLog(requestDto, member);
        return ResponseEntity.created(UriCreator.createURI(response.getId())).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WakeupLogDto.Response> getWakeupLogById(
            @PathVariable Long id,
            @AuthenticationPrincipal Member member) {

        WakeupLogDto.Response response = wakeupLogService.getWakeupLogById(id, member);
        return ResponseEntity.ok(response);

    }

    @PatchMapping("/{id}")
    public ResponseEntity<WakeupLogDto.Response> updateWakeupLog(
            @PathVariable Long id,
            @RequestBody @Valid WakeupLogDto.UpdateRequest requestDto,
            @AuthenticationPrincipal Member member) {

        WakeupLogDto.Response response = wakeupLogService.updateWakeupLog(id, requestDto, member);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWakeupLog(
            @PathVariable Long id,
            @AuthenticationPrincipal Member member) {

        wakeupLogService.deleteWakeupLog(id, member);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<WakeupLogDto.Response>> getWakeupLogs(@AuthenticationPrincipal Member member) {

        List<WakeupLogDto.Response> logs = wakeupLogService.getAllWakeupLog(member);
        return ResponseEntity.ok(logs);
    }
}
