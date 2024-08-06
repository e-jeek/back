package com.ejeek.back.challenge;

import com.ejeek.back.challenge.challenge_member.ChallengeMemberDto;
import com.ejeek.back.global.response.MultiResponse;
import com.ejeek.back.global.utils.UriCreator;
import com.ejeek.back.member.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/challenges")
@RequiredArgsConstructor
public class ChallengeController {

    private final ChallengeService challengeService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<ChallengeDto.Response> createChallenge(@Valid @RequestPart ChallengeDto.Request request,
                    @RequestPart(required = false) MultipartFile file) {
        // TODO : @AuthenticationPrincipal MemberPrincipal 에서 Member 가져오기
        Member member = new Member(1L, "test@test.com");
        ChallengeDto.Response response = challengeService.createChallenge(member, request, file);
        return ResponseEntity.created(UriCreator.createURI(response.getId())).body(response);
    }

    @PatchMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ChallengeDto.Response> updateChallenge(@PathVariable(value = "id") Long challengeId,
                    @RequestPart ChallengeDto.Request request, @RequestPart MultipartFile file) {
        // TODO : @AuthenticationPrincipal MemberPrincipal 에서 Member 가져오기
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
    public ResponseEntity<ChallengeDto.Response> deleteChallenge(@PathVariable(value = "id") Long challengeId) {
        // TODO : @AuthenticationPrincipal MemberPrincipal 에서 Member 가져오기
        Member member = new Member(1L, "test@test.com");
        challengeService.deleteChallenge(challengeId, member);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/participate")
    @ResponseBody
    public ResponseEntity<ChallengeMemberDto> participateChallenge(@PathVariable(value = "id") Long challengeId) {
        // TODO : @AuthenticationPrincipal MemberPrincipal 에서 Member 가져오기
        Member member = new Member(1L, "test@test.com");
        ChallengeMemberDto response = challengeService.participateChallenge(challengeId, member);
        return ResponseEntity.created(UriCreator.createURI(response.getId())).body(response);
    }

    @DeleteMapping("/{id}/participate")
    @ResponseBody
    public ResponseEntity<ChallengeMemberDto> withdrawChallenge(@PathVariable(value = "id") Long challengeId) {
        // TODO : @AuthenticationPrincipal MemberPrincipal 에서 Member 가져오기
        Member member = new Member(1L, "test@test.com");
        challengeService.withdrawChallenge(challengeId, member);
        return ResponseEntity.noContent().build();
    }
}
