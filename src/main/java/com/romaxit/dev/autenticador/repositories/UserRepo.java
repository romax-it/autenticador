package com.romaxit.dev.autenticador.repositories;

import com.romaxit.dev.autenticador.domain.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepo extends CrudRepository<UserEntity,Integer> {

    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByUsernameOrEmail(String username, String email);

}