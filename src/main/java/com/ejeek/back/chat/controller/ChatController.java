package com.ejeek.back.chat.controller;

import com.ejeek.back.chat.dto.ChatDto;
import com.ejeek.back.chat.service.ChatService;
import com.ejeek.back.global.response.MultiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @ResponseBody
    @GetMapping(value = "/api/challenges/{id}/chats", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<MultiResponse<ChatDto.Response>> getChattingList(@PathVariable("id") Long challengeId,
                    @PageableDefault(sort = "createdAt") Pageable pageable) {
        Slice<ChatDto.Response> response = chatService.getByChallengeId(challengeId, pageable);
        return ResponseEntity.ok(new MultiResponse<>(response));
    }

    @MessageMapping("/chat.sendMessage/{id}")
    @SendTo("/topic/challenges/{id}")
    public ChatDto.Response sendMessage(@DestinationVariable("id") Long challengeId,
                    @Header("simpSessionAttributes") Map<String, Object> simpSessionAttributes,
                    @Payload ChatDto.Request chatRequest) {
        return chatService.createChat(chatRequest, challengeId, simpSessionAttributes);
    }
}
