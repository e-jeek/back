package com.ejeek.back.feed.service;

import com.ejeek.back.feed.entity.Feed;
import com.ejeek.back.feed.repository.FeedRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FeedService {
    private final FeedRepository feedRepository;



}
