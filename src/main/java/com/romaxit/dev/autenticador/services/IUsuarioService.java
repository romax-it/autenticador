package com.romaxit.dev.autenticador.services;

import com.romaxit.dev.autenticador.core.auxiliar.ResultSearchData;
import com.romaxit.dev.autenticador.core.exceptions.ResourceNotFoundException;
import com.romaxit.dev.autenticador.domain.dto.UsuarioDto;

import java.util.List;

public interface IUsuarioService<T> {
    UsuarioDto getUserLogged();

    UsuarioDto findByUsername(String name);
    List<UsuarioDto> findAllStudends ();
    List<UsuarioDto> findAllTeachers ();

    T create(T T) throws ResourceNotFoundException;

    void delete(T T) throws ResourceNotFoundException;

    void deleteById(int id) throws ResourceNotFoundException;

    Iterable<T> findAll();

    T findByToken(String codigoToken) throws ResourceNotFoundException;

    T update(T T) throws ResourceNotFoundException;

    ResultSearchData<T> findAllSearch(int page, int size, String sortBy, String sortOrder );
}
