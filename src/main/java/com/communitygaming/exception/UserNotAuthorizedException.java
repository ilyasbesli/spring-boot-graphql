package com.communitygaming.exception;

import org.springframework.security.core.AuthenticationException;

import java.io.Serial;

/**
 * This exception is thrown in case of a not authorized user.
 */
public class UserNotAuthorizedException extends AuthenticationException {

    @Serial
    private static final long serialVersionUID = 1L;

    public UserNotAuthorizedException() {
        super("User not authorized");
    }

    public UserNotAuthorizedException(String message) {
        super(message);
    }

    public UserNotAuthorizedException(String message, Throwable t) {
        super(message, t);
    }
}
