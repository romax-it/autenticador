package com.romaxit.dev.autenticador.services.impl;

import com.romaxit.dev.autenticador.domain.dto.MailDto;

public interface IEmailService {
    void sendMail(MailDto emailData);
}
