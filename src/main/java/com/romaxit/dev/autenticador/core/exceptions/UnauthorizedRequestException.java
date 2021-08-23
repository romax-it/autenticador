package com.romaxit.dev.autenticador.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class UnauthorizedRequestException extends RuntimeException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new unauthorized request exception.
     */
    public UnauthorizedRequestException() {
        super();
    }

    /**
     * Instantiates a new unauthorized request exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public UnauthorizedRequestException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new unauthorized request exception.
     *
     * @param message the message
     */
    public UnauthorizedRequestException(final String message) {
        super(message);
    }

    /**
     * Instantiates a new unauthorized request exception.
     *
     * @param cause the cause
     */
    public UnauthorizedRequestException(final Throwable cause) {
        super(cause);
    }

}
