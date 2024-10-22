package com.ejeek.back.chat.service;

import com.ejeek.back.challenge.entity.Challenge;
import com.ejeek.back.challenge.repository.ChallengeRepository;
import com.ejeek.back.chat.dto.ChatDto;
import com.ejeek.back.chat.entity.Chat;
import com.ejeek.back.chat.mapper.ChatMapper;
import com.ejeek.back.chat.repository.ChatRepository;
import com.ejeek.back.global.exception.CustomException;
import com.ejeek.back.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {

    private final ChallengeRepository challengeRepository;
    private final ChatRepository chatRepository;
    private final ChatMapper chatMapper;

    public Slice<ChatDto.Response> getByChallengeId(Long challengeId, Pageable pageable) {
        Challenge challenge = findVerifiedChallenge(challengeId);
        Slice<Chat> chattingList = chatRepository.findAllByChallenge(challenge, pageable);
//        return chatMapper.toSliceResponse(chattingList);
        return null;
    }

    public ChatDto.Response createChat(ChatDto.Request request, Long challengeId, Map<String, Object> simpSessionAttributes) {
        Chat chat = new Chat();
        //        Chat chat = chatMapper.toChat(request, challengeId);
        Chat save = chatRepository.save(chat);
//        return chatMapper.toResponse(save);
        return null;
    }

    private Challenge findVerifiedChallenge(Long challengeId) {
        Optional<Challenge> challenge = challengeRepository.findById(challengeId);
        return challenge.orElseThrow(() -> new CustomException(ExceptionCode.CHALLENGE_NOT_FOUND));
    }
}
