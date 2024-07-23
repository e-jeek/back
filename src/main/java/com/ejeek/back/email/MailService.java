package com.ejeek.back.email;

import com.ejeek.back.global.redis.RedisUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.UUID;


@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    @Value("${email.from.address}")
    private String FROM_ADDRESS;
    private final ApplicationEventPublisher publisher;
    private final RedisUtil redisUtil;


    public void sendCertificationMail(String email) throws MessagingException {
        String ePw = createKey();

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(email);
        helper.setFrom(FROM_ADDRESS);
        helper.setSubject("WORKOUTWITH의 회원가입 인증 코드입니다.");

        Context context = new Context();
        context.setVariable("ePw", ePw);
        String html = templateEngine.process("email", context);
        helper.setText(html, true);

        helper.addInline("image", new ClassPathResource("static/logo.png"));

        log.info("이메일 전송 email = {}", email);
        mailSender.send(message);
        publisher.publishEvent(new MailSendApplicationEvent(this, email, ePw));
    }

    public boolean confirm(MailDto emailDto) {
        log.info("인증 번호 확인 email = {}, code = {}", emailDto.getEmail(), emailDto.getCode());
        String email = redisUtil.getData(emailDto.getCode());
        return email != null && email.equals(emailDto.getEmail());
    }

    private static String createKey() {
        return UUID.randomUUID().toString().substring(0, 6);
    }
}
