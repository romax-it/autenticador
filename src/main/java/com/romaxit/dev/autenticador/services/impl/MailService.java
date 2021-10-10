package com.romaxit.dev.autenticador.services.impl;

import com.romaxit.dev.autenticador.domain.dto.MailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService implements IEmailService {

    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String emailUsernameOrigin;

    @Autowired
    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendMail(MailDto emailData) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(emailData.getTo());
        email.setSubject(emailData.getSubject());
        email.setText(emailData.getMensaje());
        email.setFrom(emailUsernameOrigin);
        try {
            mailSender.send(email);
        } catch (MailException e) {
            throw new MailAuthenticationException("No fue posible enviar el correo electrónico", e);
        }
    }

}
