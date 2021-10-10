package com.romaxit.dev.autenticador.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MailDto {
    private String from;
    private String to;
    private String subject;
    private String mensaje;
}
