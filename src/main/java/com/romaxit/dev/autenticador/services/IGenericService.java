package com.romaxit.dev.autenticador.services;


import com.romaxit.dev.autenticador.core.auxiliar.ResultSearchData;
import com.romaxit.dev.autenticador.core.exceptions.ResourceNotFoundException;

public interface IGenericService<T> {
    T create(T T) throws ResourceNotFoundException;

    void delete(T T) throws ResourceNotFoundException;

    void deleteById(int id) throws ResourceNotFoundException;

    Iterable<T> findAll();

    T findById(int id) throws ResourceNotFoundException;

    T update(T T) throws ResourceNotFoundException;

    ResultSearchData<T> findAllSearch(int page, int size, String sortBy, String sortOrder );
}