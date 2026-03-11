package com.example.demo.exception;

/**
 * Thrown by UserService when a registration or update attempts to use
 * an email address that is already registered to a different user.
 *
 * The GlobalExceptionHandler maps this to HTTP 409 Conflict.
 */
public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException(String email) {
        super("Email already in use: " + email);
    }
}
