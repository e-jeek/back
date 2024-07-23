package com.ejeek.back.email;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class MailSendApplicationEvent extends ApplicationEvent {

    private final String email;
    private final String code;

    public MailSendApplicationEvent(Object source, String email, String code) {
        super(source);
        this.email = email;
        this.code = code;
    }
}
