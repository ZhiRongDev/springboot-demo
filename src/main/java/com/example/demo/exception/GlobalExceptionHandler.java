package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * GlobalExceptionHandler centralises all error responses in one place.
 *
 * @RestControllerAdvice — this is the key annotation. It combines:
 *   @ControllerAdvice  — applies this class to ALL @RestController beans in the app.
 *   @ResponseBody      — tells Spring to serialize the return value as JSON.
 *
 * How it works:
 *   When ANY controller method throws an exception, Spring's DispatcherServlet
 *   looks for an @ExceptionHandler method in the registered @ControllerAdvice beans
 *   that matches the exception type. If found, it calls that handler instead of
 *   returning a generic 500 error.
 *
 * Without this class, Spring Boot would still return error JSON (via BasicErrorController)
 * but with less control over the format. This gives us a consistent error shape:
 *   { "error": "...", "fieldErrors": {...} }
 *
 * Dependency injection note:
 *   This class itself is a Spring bean. Spring discovers it via @ComponentScan
 *   (triggered by @SpringBootApplication) and registers it automatically.
 *   No explicit wiring needed.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles UserNotFoundException → HTTP 404 Not Found.
     *
     * @ExceptionHandler(UserNotFoundException.class) tells Spring:
     * "when a UserNotFoundException is thrown anywhere in a controller, call this method."
     *
     * ResponseEntity<T> lets us control the HTTP status code AND the response body.
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)                    // 404
                .body(Map.of("error", ex.getMessage()));
    }

    /**
     * Handles EmailAlreadyExistsException → HTTP 409 Conflict.
     */
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleEmailExists(EmailAlreadyExistsException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)                     // 409
                .body(Map.of("error", ex.getMessage()));
    }

    /**
     * Handles InvalidCredentialsException → HTTP 401 Unauthorized.
     */
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleInvalidCredentials(InvalidCredentialsException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)                 // 401
                .body(Map.of("error", ex.getMessage()));
    }

    /**
     * Handles IllegalArgumentException → HTTP 400 Bad Request.
     * Used when the service rejects a logically invalid request (e.g., empty update body).
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleBadRequest(IllegalArgumentException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", ex.getMessage()));
    }

    /**
     * Handles MethodArgumentNotValidException → HTTP 400 Bad Request.
     *
     * This exception is thrown automatically by Spring MVC when @Valid fails on a
     * @RequestBody parameter. It contains a list of FieldError objects — one per
     * failing validation constraint.
     *
     * We extract each field name and its error message to build a helpful response:
     * {
     *   "error": "Validation failed",
     *   "fieldErrors": {
     *     "email": "Invalid email format",
     *     "password": "Password must be at least 6 characters"
     *   }
     * }
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();

        // getBindingResult() contains all the validation failures.
        // getAllErrors() returns them as a list of ObjectError (or FieldError subtype).
        ex.getBindingResult().getAllErrors().forEach(error -> {
            // Cast to FieldError to get the specific field name (e.g., "email", "password")
            String fieldName = ((FieldError) error).getField();
            // getDefaultMessage() returns the message= attribute from the annotation
            String message = error.getDefaultMessage();
            fieldErrors.put(fieldName, message);
        });

        Map<String, Object> body = new HashMap<>();
        body.put("error", "Validation failed");
        body.put("fieldErrors", fieldErrors);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)                  // 400
                .body(body);
    }
}
