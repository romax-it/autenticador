package com.romaxit.dev.autenticador.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends Exception {
    /**
	 *
	 */
	private static final long serialVersionUID = -7509737619336894337L;


    public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
    }

    public ResourceNotFoundException(final String message) {
        super("El recurso: " + message + " Ya existe.");
    }
}