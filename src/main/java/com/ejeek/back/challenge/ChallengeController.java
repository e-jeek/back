package com.ejeek.back.challenge;

import com.ejeek.back.challenge.challenge_confirm.ChallengeConfirmDto;
import com.ejeek.back.challenge.challenge_member.ChallengeMemberDto;
import com.ejeek.back.global.response.MultiResponse;
import com.ejeek.back.global.utils.UriCreator;
import com.ejeek.back.member.entity.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/challenges")
@RequiredArgsConstructor
public class ChallengeController {

    private final ChallengeService challengeService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<ChallengeDto.Response> createChallenge(@AuthenticationPrincipal Member member,
                    @Valid @RequestPart ChallengeDto.Request request, @RequestPart(required = false) MultipartFile file) {
        ChallengeDto.Response response = challengeService.createChallenge(member, request, file);
        return ResponseEntity.created(UriCreator.createURI(response.getId())).body(response);
    }

    @PatchMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ChallengeDto.Response> updateChallenge(@AuthenticationPrincipal Member member,
                    @PathVariable(value = "id") Long challengeId, @RequestPart ChallengeDto.Request request,
                    @RequestPart(required = false) MultipartFile file) {
        ChallengeDto.Response response = challengeService.modifyChallenge(challengeId, member, request, file);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ChallengeDto.Response> getChallenge(@PathVariable(value = "id") Long challengeId) {
        ChallengeDto.Response response = challengeService.getChallenge(challengeId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<MultiResponse<ChallengeDto.Response>> getChallenges(@PageableDefault(size = 30) Pageable pageable) {
        Slice<ChallengeDto.Response> response = challengeService.getChallenges(pageable);
        return ResponseEntity.ok(new MultiResponse<>(response.getContent(), response));
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ChallengeDto.Response> deleteChallenge(@AuthenticationPrincipal Member member,
                    @PathVariable(value = "id") Long challengeId) {
        challengeService.deleteChallenge(challengeId, member);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/participation")
    @ResponseBody
    public ResponseEntity<ChallengeMemberDto.Response> participateChallenge(@AuthenticationPrincipal Member member,
                    @PathVariable(value = "id") Long challengeId) {
        ChallengeMemberDto.Response response = challengeService.participateChallenge(challengeId, member);
        return ResponseEntity.created(UriCreator.createURI(response.getId())).body(response);
    }

    @DeleteMapping("/{id}/participation")
    @ResponseBody
    public ResponseEntity<ChallengeMemberDto> withdrawChallenge(@AuthenticationPrincipal Member member,
                    @PathVariable(value = "id") Long challengeId) {
        challengeService.withdrawChallenge(challengeId, member);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/confirmations")
    @ResponseBody
    public ResponseEntity<ChallengeConfirmDto.Response> submitConfirmations(@AuthenticationPrincipal Member member,
                    @PathVariable(value = "id") Long challengeId, @RequestPart ChallengeConfirmDto.Request request,
                    @RequestPart MultipartFile file) {
        ChallengeConfirmDto.Response response = challengeService.submitConfirmation(challengeId, member, request, file);
        return ResponseEntity.created(UriCreator.createURI(response.getId())).body(response);
    }

    @PostMapping("/{id}/confirmations/{confirmId}")
    @ResponseBody
    public ResponseEntity<ChallengeConfirmDto.Response> approveConfirmation(@AuthenticationPrincipal Member member,
                    @PathVariable(value = "id") Long challengeId, @PathVariable Long confirmId, @RequestParam Boolean confirm) {
        ChallengeConfirmDto.Response response = challengeService.approveConfirmation(member, challengeId, confirmId, confirm);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/confirmations")
    @ResponseBody
    public ResponseEntity<MultiResponse<ChallengeConfirmDto.Response>> getConfirmations(@AuthenticationPrincipal Member member,
                    @PathVariable(value = "id") Long challengeId, @RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate date,
                    @PageableDefault(size = 30) Pageable pageable) {
        Slice<ChallengeConfirmDto.Response> slice = challengeService.getConfirmationsByDate(member, challengeId, date, pageable);
        return ResponseEntity.ok(new MultiResponse<>(slice.getContent(), slice));
    }

    /**
     * 챌린지 참가자 조회
     */
    @GetMapping("/{id}/participants")
    @ResponseBody
    public ResponseEntity<MultiResponse<ChallengeMemberDto.Response>> getParticipants(
                    @PathVariable(value = "id") Long challengeId, @PageableDefault(size = 30) Pageable pageable) {
        Slice<ChallengeMemberDto.Response> response = challengeService.getParticipants(challengeId, pageable);
        return ResponseEntity.ok(new MultiResponse<>(response.getContent(), response));
    }
}
