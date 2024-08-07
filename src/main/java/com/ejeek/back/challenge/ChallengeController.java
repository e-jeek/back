package com.ejeek.back.challenge;

import com.ejeek.back.challenge.challenge_confirm.ChallengeConfirmDto;
import com.ejeek.back.challenge.challenge_member.ChallengeMemberDto;
import com.ejeek.back.global.response.MultiResponse;
import com.ejeek.back.global.utils.UriCreator;
import com.ejeek.back.member.Member;
import com.ejeek.back.member.MemberPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/challenges")
@RequiredArgsConstructor
public class ChallengeController {

    private final ChallengeService challengeService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<ChallengeDto.Response> createChallenge(@AuthenticationPrincipal MemberPrincipal principal,
                    @Valid @RequestPart ChallengeDto.Request request, @RequestPart(required = false) MultipartFile file) {
        Member member = new Member(1L, "test@test.com");
        ChallengeDto.Response response = challengeService.createChallenge(member, request, file);
        return ResponseEntity.created(UriCreator.createURI(response.getId())).body(response);
    }

    @PatchMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ChallengeDto.Response> updateChallenge(@AuthenticationPrincipal MemberPrincipal principal,
                    @PathVariable(value = "id") Long challengeId, @RequestPart ChallengeDto.Request request,
                    @RequestPart(required = false) MultipartFile file) {
        Member member = new Member(1L, "test@test.com");
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
    public ResponseEntity<ChallengeDto.Response> deleteChallenge(@AuthenticationPrincipal MemberPrincipal principal,
                    @PathVariable(value = "id") Long challengeId) {
        Member member = new Member(1L, "test@test.com");
        challengeService.deleteChallenge(challengeId, member);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/participation")
    @ResponseBody
    public ResponseEntity<ChallengeMemberDto.Response> participateChallenge(@AuthenticationPrincipal MemberPrincipal principal,
                    @PathVariable(value = "id") Long challengeId) {
        Member member = new Member(1L, "test@test.com");
        ChallengeMemberDto.Response response = challengeService.participateChallenge(challengeId, member);
        return ResponseEntity.created(UriCreator.createURI(response.getId())).body(response);
    }

    @DeleteMapping("/{id}/participation")
    @ResponseBody
    public ResponseEntity<ChallengeMemberDto> withdrawChallenge(@AuthenticationPrincipal MemberPrincipal principal,
                    @PathVariable(value = "id") Long challengeId) {
        Member member = new Member(1L, "test@test.com");
        challengeService.withdrawChallenge(challengeId, member);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/confirmation")
    @ResponseBody
    public ResponseEntity<ChallengeConfirmDto.Response> confirmChallenge(@AuthenticationPrincipal MemberPrincipal principal,
                    @PathVariable(value = "id") Long challengeId, @RequestPart ChallengeConfirmDto.Request request,
                    @RequestPart MultipartFile file) {
        Member member = new Member(1L, "test@test.com");
        ChallengeConfirmDto.Response response = challengeService.confirmChallenge(challengeId, member, request, file);
        return ResponseEntity.created(UriCreator.createURI(response.getId())).body(response);
    }

    // TODO 특정 Challenge 에 참여 중인 모든 member GetMapping API 필요
    // TODO 관리자 -> 특정 challengeConfirm 을 confirm 을 true 로 변경할 수 있도록 하는 Mapping API 필요
}
