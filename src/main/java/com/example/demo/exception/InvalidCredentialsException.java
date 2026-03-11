package com.example.demo.exception;

/**
 * Thrown by UserService when login fails (wrong email or wrong password).
 *
 * Deliberately uses the same message for both "email not found" and "wrong password"
 * cases. This is a security best practice: an attacker cannot determine whether
 * an email address is registered by observing which error they get.
 *
 * The GlobalExceptionHandler maps this to HTTP 401 Unauthorized.
 */
public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException() {
        super("Invalid email or password");
    }
}
