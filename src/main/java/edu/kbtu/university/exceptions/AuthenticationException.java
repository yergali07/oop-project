package edu.kbtu.university.exceptions;

/**
 * Thrown when a user fails authentication (invalid credentials, locked account, etc.).
 */
public class AuthenticationException extends Exception {

    private static final long serialVersionUID = 1L;

    public AuthenticationException() {
        super("Authentication failed");
    }

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
