package com.ejeek.back.feed.repository;

import com.ejeek.back.feed.entity.Feed;
import com.ejeek.back.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeedRepository extends JpaRepository<Feed, Long> {
    List<Feed> findByMemberNickname (String memberNickname);
}