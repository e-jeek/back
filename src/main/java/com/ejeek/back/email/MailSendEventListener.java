package com.ejeek.back.email;

import com.ejeek.back.global.redis.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Timer;
import java.util.TimerTask;

@RequiredArgsConstructor
@Component
@Slf4j
public class MailSendEventListener {

    private final RedisUtil redisUtil;

    @Async
    @EventListener
    public void listen(MailSendApplicationEvent event) {
        log.info("Redis 인증 번호, 이메일 임시 저장");
        redisUtil.setDataExpire(event.getCode(), event.getEmail(), 60 * 3L);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timer.cancel();
                log.info("3분 초과. 인증 번호 자동 삭제");
            }
        }, 60 * 3 * 1000);
    }
}
