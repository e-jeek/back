package com.ejeek.back.chat.dto;

import com.ejeek.back.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class ChatDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {

        private MessageType type;
        private Long memberId;
        private String content;
    }


    @Getter
    public static class Response {

        private final Long chatId;
        private final Long memberId;
        private final String nickname;
        private final String profileImgUrl;
        private final MessageType type;
        private final LocalDateTime createdAt;
        private final String content;

        @Builder
        public Response(Long chatId, Long memberId, String nickname, String profileImgUrl, MessageType type, LocalDateTime createdAt,
                        String content) {
            this.chatId = chatId;
            this.memberId = memberId;
            this.nickname = nickname;
            this.profileImgUrl = profileImgUrl;
            this.type = type;
            this.createdAt = createdAt;
            this.content = content;
        }
    }
}
