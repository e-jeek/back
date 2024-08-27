package com.ejeek.back.feed.controller;


import com.ejeek.back.enums.Gender;
import com.ejeek.back.enums.MemberStatus;
import com.ejeek.back.enums.Role;
import com.ejeek.back.feed.dto.FeedDto;
import com.ejeek.back.feed.service.FeedService;
import com.ejeek.back.member.entity.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.metadata.aggregated.rule.VoidMethodsMustNotBeReturnValueConstrained;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/feeds")
@RestController
@Slf4j
public class FeedApiController {
    private final FeedService feedService;
    /*
    Member member = new Member(
            1L,  // ID
            "example@example.com",  // Email
            "encryptedpassword",    // Password
            "John Doe",              // Name
            "johnny",                // Nickname
            MemberStatus.ACTIVE,     // Status
            Role.USER,               // Role
            "This is a sample member.", // Content
            LocalDate.of(1990, 1, 1),  // Birth
            Gender.MAN,             // Gender
            180,                     // Height
            75,                      // Weight
            true,                    // Policy
            true                     // Marketing
    );

     */
    @PostMapping
    public ResponseEntity<FeedDto.FeedResponse> createFeed(@AuthenticationPrincipal Member member, @Valid @RequestBody FeedDto.FeedRequest request, @RequestPart MultipartFile file){
        FeedDto.FeedResponse createdFeed = feedService.createFeed(member, request, file);

        return ResponseEntity.ok(createdFeed);
    }

    @GetMapping("/{feedId}")
    public ResponseEntity<FeedDto.FeedResponse> getFeed(@PathVariable Long feedId){
        FeedDto.FeedResponse feed = feedService.getFeed(feedId);
        return ResponseEntity.ok(feed);
    }

    @GetMapping("/{member}")
    public ResponseEntity<List<FeedDto.FeedResponse>> getMemberFeed(@PathVariable Member member){
        List<FeedDto.FeedResponse> feedLists = feedService.getAllFeed(member);
        return ResponseEntity.ok(feedLists);

    }

    @PatchMapping("/{feedId}")
    public ResponseEntity<FeedDto.FeedResponse> updateFeed(@AuthenticationPrincipal Member member , @PathVariable Long feedId, @Valid @RequestBody FeedDto.FeedUpdateRequest request, @RequestPart MultipartFile file){
        FeedDto.FeedResponse updateFeed = feedService.updateFeed(feedId, member, request, file);
        return ResponseEntity.ok(updateFeed);
    }

    @DeleteMapping("/{feedId}")
    public ResponseEntity<FeedDto.FeedResponse> deleteFeed(@AuthenticationPrincipal Member member, @PathVariable Long feedId){
        feedService.deleteFeed(feedId, member);
        return ResponseEntity.noContent().build();

    }









}
