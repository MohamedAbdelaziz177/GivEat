package com.abdelaziz26.GivEat.Utils;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailUtil {

    private final JavaMailSender mailSender;

    @Value("${SMTP_CLIENT_PROD}")
    private String from;

    public void sendSimpleMail(String to, String subject, String body) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject("CisHackIt - " + subject);
        simpleMailMessage.setText(body);

        mailSender.send(simpleMailMessage);
    }
}
