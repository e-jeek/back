package com.ejeek.back.chat.service;

import com.ejeek.back.challenge.repository.ChallengeMemberRepository;
import com.ejeek.back.global.exception.CustomException;
import com.ejeek.back.global.exception.ExceptionCode;
import com.ejeek.back.global.jwt.provider.TokenProvider;
import com.ejeek.back.member.entity.Member;
import com.ejeek.back.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {

    public static final String DEFAULT_PATH = "/topic/public/";
    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;
    private final ChallengeMemberRepository challengeMemberRepository;

    // websocket을 통해 들어온 요청이 처리 되기전 실행
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        StompCommand command = accessor.getCommand();

        if (StompCommand.CONNECT.equals(command)) { // websocket 연결 요청 -> JWT 인증
            // JWT 인증
            String autheHeaderValue = accessor.getFirstNativeHeader(AUTHORIZATION);
            Member member = getMemberByAuthorizationHeader(autheHeaderValue);
            // 인증 후 데이터를 헤더에 추가
            setValue(accessor, "memberId", member.getId());
            setValue(accessor, "nickname", member.getNickname());
            //			setValue(accessor, "profileImgUrl", member.Image());
        } else if (StompCommand.SUBSCRIBE.equals(command)) { // 채팅룸 입장 -> ChallengeMember 인지 검증
            Long memberId = (Long) getValue(accessor, "memberId");
            Long challengeId = parseChallengeIdFromPath(accessor);
            log.debug("memberId : " + memberId + "challengeId : " + challengeId);
            setValue(accessor, "challengeId", challengeId);
            validateMemberInChallenge(memberId, challengeId);
        } else if (StompCommand.DISCONNECT == command) { // Websocket 연결 종료
            Long memberId = (Long) getValue(accessor, "memberId");
            log.info("DISCONNECTED memberId : {}", memberId);
        }

        log.info("header : " + message.getHeaders());
        log.info("message:" + message);
        return message;
    }

    private Member getMemberByAuthorizationHeader(String authHeaderValue) {
        String accessToken = getTokenByAuthorizationHeader(authHeaderValue);
        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        Member member = (Member) authentication.getPrincipal();
        String email = member.getEmail();
        return memberRepository.findByEmail(email).orElseThrow(() -> new CustomException(ExceptionCode.MEMBER_NOT_EXIST));
    }

    private String getTokenByAuthorizationHeader(String authHeaderValue) {
        if (Objects.isNull(authHeaderValue) || authHeaderValue.isBlank()) {
            throw new CustomException(ExceptionCode.WEBSOCKET_HEADER_ERROR);
        }
        String accessToken = tokenProvider.resolveToken(authHeaderValue);
        tokenProvider.validateToken(accessToken);
        return accessToken;
    }

    private Long parseChallengeIdFromPath(StompHeaderAccessor accessor) {
        String destination = accessor.getDestination();
        return Long.parseLong(destination.substring(DEFAULT_PATH.length()));
    }

    private void validateMemberInChallenge(Long memberId, Long challengeId) {
        challengeMemberRepository.findByChallengeIdAndMemberId(challengeId, memberId)
                        .orElseThrow(() -> new CustomException(ExceptionCode.CHALLENGE_MEMBER_NOT_FOUND));
    }

    private Object getValue(StompHeaderAccessor accessor, String key) {
        Map<String, Object> sessionAttributes = getSessionAttributes(accessor);
        Object value = sessionAttributes.get(key);
        if (Objects.isNull(value)) {
            throw new CustomException(ExceptionCode.WEBSOCKET_KEY_ERROR);
        }
        return value;
    }

    private void setValue(StompHeaderAccessor accessor, String key, Object value) {
        Map<String, Object> sessionAttributes = getSessionAttributes(accessor);
        sessionAttributes.put(key, value);
    }

    private Map<String, Object> getSessionAttributes(StompHeaderAccessor accessor) {
        Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
        if (Objects.isNull(sessionAttributes)) {
            throw new CustomException(ExceptionCode.WEBSOCKET_ATTRIBUTES_ERROR);
        }
        return sessionAttributes;
    }
}
