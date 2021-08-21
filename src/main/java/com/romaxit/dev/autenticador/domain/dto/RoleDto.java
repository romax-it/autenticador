package com.romaxit.dev.autenticador.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDto {
    String nombre;
    public RoleDto(String nombre) {
        this.setNombre(nombre);
    }
}
