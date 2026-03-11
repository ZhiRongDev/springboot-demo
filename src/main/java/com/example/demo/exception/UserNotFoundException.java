package com.example.demo.exception;

/**
 * Thrown by UserService when a requested user ID does not exist.
 *
 * Extends RuntimeException — an "unchecked" exception. This means:
 *   - The caller doesn't need to declare it in a throws clause.
 *   - Spring can intercept it automatically with @ExceptionHandler.
 *
 * The GlobalExceptionHandler catches this and maps it to HTTP 404.
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super("User not found with id: " + id);
    }
}
