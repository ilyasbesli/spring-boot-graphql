package com.communitygaming.exception;

import org.springframework.security.core.AuthenticationException;

import java.io.Serial;

/**
 * This exception is thrown in case of a not found tournament.
 */
public class TournamentNotFoundException extends AuthenticationException {

    @Serial
    private static final long serialVersionUID = 1L;

    public TournamentNotFoundException() {
        super("Tournament not found");
    }

    public TournamentNotFoundException(String message) {
        super(message);
    }

    public TournamentNotFoundException(String message, Throwable t) {
        super(message, t);
    }
}
