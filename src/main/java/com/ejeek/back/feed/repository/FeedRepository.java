package com.ejeek.back.feed.repository;

import com.ejeek.back.feed.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Long> {
    List<Feed> findByMemberNickname (String memberNickname);
}