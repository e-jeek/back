package com.ejeek.back.feed.service;

import com.ejeek.back.feed.dto.FeedDto;
import com.ejeek.back.feed.entity.Feed;
import com.ejeek.back.feed.mapper.FeedMapper;
import com.ejeek.back.feed.repository.FeedRepository;
import com.ejeek.back.global.exception.CustomException;
import com.ejeek.back.global.exception.ExceptionCode;
import com.ejeek.back.hashtag.Hashtag;
import com.ejeek.back.hashtag.HashtagService;
import com.ejeek.back.image.Image;
import com.ejeek.back.image.ImageService;
import com.ejeek.back.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FeedService {

    private final FeedRepository feedRepository;
    private final FeedMapper feedMapper;
    private final ImageService imageService;
    private final HashtagService hashtagService;

    @Transactional
    public FeedDto.FeedResponse createFeed(Member member, FeedDto.FeedRequest request, MultipartFile image) {
        Feed feed = feedMapper.toFeedEntity(request, member);
        Feed savedFeed = feedRepository.save(feed);

        List<Hashtag> hashtags = hashtagService.createHashtags(request.getHashtags(), savedFeed);
        savedFeed.updateHashtags(hashtags);

        Optional.ofNullable(image).ifPresent(file -> {
            Image img = imageService.createImage(file, savedFeed);
            savedFeed.updateImgUrl(img);
        });


        return feedMapper.toFeedDto(savedFeed);


    }

    @Transactional(readOnly = true)
    public FeedDto.FeedResponse getFeed(Long feedId) {
        Feed feed = verifyFeed(feedId);
        return feedMapper.toFeedDto(feed);
    }


    @Transactional(readOnly = true)
    public List<FeedDto.FeedResponse> getAllFeed(String memberNickname) {
        List<Feed> feeds = feedRepository.findByMemberNickname(memberNickname);
        List<FeedDto.FeedResponse> feedList = new ArrayList<>();
        feeds.forEach(s -> feedList.add(feedMapper.toFeedDto(s)));
        return feedList;
    }



    @Transactional
    public FeedDto.FeedResponse updateFeed(Long feedId, Member member, FeedDto.FeedUpdateRequest request, MultipartFile image) {
        Feed feed = isAuthorized(feedId, member);
        feed.updateFeedDto(request);

        List<Hashtag> hashtags = hashtagService.createHashtags(request.getHashtags(), feed);
        feed.updateHashtags(hashtags);


        Optional.ofNullable(image).ifPresent(file -> {
            Image img = imageService.createImage(file, feed);
            feed.updateImgUrl(img);
        });

        return feedMapper.toFeedUpdateDto(feed);

    }

    @Transactional
    public void deleteFeed(Long feedId, Member member) {
        Feed feed = isAuthorized(feedId, member);

        feedRepository.delete(feed);
    }


    // 유효한 피드인지 검증
    private Feed verifyFeed(Long feedId) {
        return feedRepository.findById(feedId)
                .orElseThrow(() -> new CustomException(ExceptionCode.MEMBER_NOT_SAME)); // 변경해야함
    }

    // 권한이 있는 사용자인지 검증
    private Feed isAuthorized(Long feedId, Member member) {
        Feed feed = verifyFeed(feedId);
        if (!feed.getMember().getId().equals(member.getId())) {
            throw new CustomException(ExceptionCode.MEMBER_NOT_SAME);
        } else {
            return feed;
        }

    }
}