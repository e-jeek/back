package com.ejeek.back.chat.service;

import com.ejeek.back.chat.dto.ChatDto;
import com.ejeek.back.enums.MessageType;
import com.ejeek.back.global.exception.CustomException;
import com.ejeek.back.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messagingTemplate;

    // 연결 요청
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        log.info("Received a new web socket connection");
    }

    // 입장
    @EventListener
    public void handleWebSocketSubscribeListener(SessionSubscribeEvent event) {
        log.info("Received a new web socket subscribe");
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());

        String nickname = (String) getValue(accessor, "nickname");
        Long memberId = (Long) getValue(accessor, "memberId");
        Long challengeId = (Long) getValue(accessor, "challengeId");

        log.info("User: {} {} Participate challenge : {}", memberId, nickname, challengeId);
        ChatDto.Request chatRequest = new ChatDto.Request(MessageType.JOIN, memberId, nickname + " 님이 입장했습니다.");
        messagingTemplate.convertAndSend("/topic/public/" + challengeId, chatRequest);
    }

    // 연결 해제
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());

        String nickname = (String) getValue(accessor, "nickname");
        Long memberId = (Long) getValue(accessor, "memberId");
        Long challengeId = (Long) getValue(accessor, "challengeId");

        log.info("User: {} {} Disconnected challenge : {}", memberId, nickname, challengeId);
        ChatDto.Request chatRequest = new ChatDto.Request(MessageType.LEAVE, memberId, nickname + " 님이 떠났습니다.");
        messagingTemplate.convertAndSend("/topic/public/" + challengeId, chatRequest);
    }

    private Object getValue(StompHeaderAccessor accessor, String key) {
        Map<String, Object> sessionAttributes = getSessionAttributes(accessor);
        Object value = sessionAttributes.get(key);
        if (Objects.isNull(value)) {
            throw new CustomException(ExceptionCode.WEBSOCKET_KEY_ERROR);
        }
        return value;
    }

    private Map<String, Object> getSessionAttributes(StompHeaderAccessor accessor) {
        Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
        if (Objects.isNull(sessionAttributes)) {
            throw new CustomException(ExceptionCode.WEBSOCKET_ATTRIBUTES_ERROR);
        }
        return sessionAttributes;
    }
}
