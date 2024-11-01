package com.ejeek.back.feed.controller;


import com.ejeek.back.feed.dto.FeedDto;
import com.ejeek.back.feed.service.FeedService;
import com.ejeek.back.member.entity.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/feeds")
@RestController
@Slf4j
public class FeedApiController {
    private final FeedService feedService;




    @PostMapping
    public ResponseEntity<FeedDto.FeedResponse> createFeed(@AuthenticationPrincipal Member member, @Valid @RequestBody FeedDto.FeedRequest request , @RequestPart MultipartFile file ){
        FeedDto.FeedResponse createdFeed = feedService.createFeed(member, request, file);

        return ResponseEntity.ok(createdFeed);
    }

    @GetMapping("/{feedId}")
    public ResponseEntity<FeedDto.FeedResponse> getFeed(@PathVariable Long feedId){
        FeedDto.FeedResponse feed = feedService.getFeed(feedId);
        return ResponseEntity.ok(feed);
    }

    @GetMapping("/all/{memberNickname}")
    public ResponseEntity<List<FeedDto.FeedResponse>> getMemberFeed(@PathVariable String memberNickname){
        List<FeedDto.FeedResponse> feedLists = feedService.getAllFeed(memberNickname);
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