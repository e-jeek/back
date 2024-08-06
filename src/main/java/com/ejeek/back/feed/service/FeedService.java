package com.ejeek.back.feed.service;

import com.ejeek.back.feed.dto.FeedResponseDto;
import com.ejeek.back.feed.dto.FeedSaveRequestDto;
import com.ejeek.back.feed.dto.FeedUpdateRequestDto;
import com.ejeek.back.feed.entity.Feed;
import com.ejeek.back.feed.repository.FeedRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FeedService {
    private final FeedRepository feedRepository;

    @Transactional
    public Feed save(FeedSaveRequestDto feedSaveDto){
        return feedRepository.save(feedSaveDto.toEntity());
    }


    @Transactional
    public FeedResponseDto findById(Long id){
        Feed entity = feedRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("게시물 존재하지 않음 id = "));

        return new FeedResponseDto(entity);
    }

    @Transactional
    public Feed update(Long feedId, FeedUpdateRequestDto feedUpdateRequestDto){
        Feed feed = feedRepository.findById(feedId)
                .orElseThrow(() -> new IllegalArgumentException("게시물 존재하지 않음 id = "+feedId));
        feed.update(feedUpdateRequestDto.getContent(), feedUpdateRequestDto.getFeedImage(), feedUpdateRequestDto.getFeedHashtag(), feedUpdateRequestDto.getRefId());
        return feedRepository.save(feed);
    }





    @Transactional
    public void delete(Long id){
        Feed feed = feedRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시물 존재하지 않음 id"+id));

        feedRepository.delete(feed);

    }

}
