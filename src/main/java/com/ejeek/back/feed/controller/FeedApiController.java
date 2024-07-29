package com.ejeek.back.feed.controller;


import com.ejeek.back.feed.repository.FeedRepository;
import com.ejeek.back.feed.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Service
public class FeedApiController {
    private final FeedService feedService;






}
