package com.ejeek.back.chat.repository;

import com.ejeek.back.challenge.entity.Challenge;
import com.ejeek.back.chat.entity.Chat;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    Slice<Chat> findAllByChallenge(Challenge challenge, Pageable pageable);
}
