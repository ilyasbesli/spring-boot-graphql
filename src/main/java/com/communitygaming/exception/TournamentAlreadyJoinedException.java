package com.communitygaming.exception;

import org.springframework.security.core.AuthenticationException;

import java.io.Serial;

/**
 * This exception is thrown in case of a already joined tournament.
 */
public class TournamentAlreadyJoinedException extends AuthenticationException {

    @Serial
    private static final long serialVersionUID = 1L;

    public TournamentAlreadyJoinedException() {
        super("Tournament already joined");
    }

    public TournamentAlreadyJoinedException(String message) {
        super(message);
    }

    public TournamentAlreadyJoinedException(String message, Throwable t) {
        super(message, t);
    }
}
