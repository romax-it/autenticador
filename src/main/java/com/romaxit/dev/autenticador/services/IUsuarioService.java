package com.romaxit.dev.autenticador.services;

import com.romaxit.dev.autenticador.domain.dto.UsuarioDto;

import java.util.List;

public interface IUsuarioService<T> extends IGenericService<T> {
    UsuarioDto getUserLogged();

    UsuarioDto findByUsername(String name);
    List<UsuarioDto> findAllStudends ();
    List<UsuarioDto> findAllTeachers ();
}
