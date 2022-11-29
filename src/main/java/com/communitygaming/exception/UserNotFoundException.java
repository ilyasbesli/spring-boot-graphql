package com.communitygaming.exception;

import org.springframework.security.core.AuthenticationException;

import java.io.Serial;

/**
 * This exception is thrown in case of a not found user.
 */
public class UserNotFoundException extends AuthenticationException {

    @Serial
    private static final long serialVersionUID = 1L;

    public UserNotFoundException() {
        super("User not found");
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable t) {
        super(message, t);
    }
}
