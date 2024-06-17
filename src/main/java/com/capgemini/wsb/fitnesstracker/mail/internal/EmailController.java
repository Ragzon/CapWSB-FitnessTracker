package com.capgemini.wsb.fitnesstracker.mail.internal;

import com.capgemini.wsb.fitnesstracker.mail.api.EmailDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/send-email")
public class EmailController {

    private final EmailSenderImpl emailService;

    public EmailController(EmailSenderImpl emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendTestEmail(@RequestBody EmailDto emailDto) {

        emailService.send(emailDto);
        return ResponseEntity.ok("Email sent successfully");
    }
}
