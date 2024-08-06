package com.ejeek.back.feed.controller;


import com.ejeek.back.feed.dto.FeedSaveRequestDto;
import com.ejeek.back.feed.dto.FeedUpdateRequestDto;
import com.ejeek.back.feed.entity.Feed;
import com.ejeek.back.feed.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/feeds")
@Service
public class FeedApiController {
    private final FeedService feedService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody FeedSaveRequestDto feedSaveDto){
        Feed saveFeed = feedService.save(feedSaveDto);

        return ResponseEntity.ok(saveFeed);
    }

    @PutMapping
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody FeedUpdateRequestDto feedUpdateRequestDto){
        Feed updateFeed = feedService.update(id, feedUpdateRequestDto);
        return ResponseEntity.ok(updateFeed);
    }

    public Long delete(@PathVariable Long id){
        feedService.delete(id);
        return id;
    }







}
