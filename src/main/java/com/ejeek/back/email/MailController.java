package com.ejeek.back.email;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/emails")
public class MailController {

    private final MailService mailService;

    @PostMapping
    public ResponseEntity<String> sendEmail(@RequestParam String email) throws Exception {
        mailService.sendCertificationMail(email);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/confirm")
    public ResponseEntity<Boolean> confirm(@RequestBody MailDto emailDto) {
        return ResponseEntity.ok(mailService.confirm(emailDto));
    }
}
