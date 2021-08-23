package com.romaxit.dev.autenticador.config.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

// TODO: Auto-generated Javadoc
/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Data

/**
 * Instantiates a new login.
 */
@NoArgsConstructor

/**
 * Instantiates a new login.
 *
 * @param username the username
 * @param password the password
 */
@AllArgsConstructor
public class Login {

    /** The username. */
    @NotBlank
    @Size(max = 50)
    private String username;

    /** The password. */
    @NotBlank
    @Size(max = 255)
    private String password;

}