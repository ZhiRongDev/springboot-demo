package com.example.demo.dto;

/**
 * UserResponse is the DTO returned to the client for user-related endpoints.
 *
 * We use a Java record (Java 16+) here because:
 *   - It's immutable: once created, fields can't change — safe for output.
 *   - It auto-generates constructor, getters, equals(), hashCode(), toString().
 *   - Less boilerplate than a regular class for a simple data carrier.
 *
 * Notice: no passwordHash field. The API NEVER exposes the password (even hashed).
 *
 * Jackson can serialize records to JSON out of the box.
 * Example output: {"id": 1, "name": "Alice", "email": "alice@example.com"}
 */
public record UserResponse(Long id, String name, String email) {

    /**
     * Convenience factory method to convert a User domain object into a UserResponse.
     * This is the "mapping" step that strips out the passwordHash.
     */
    public static UserResponse from(com.example.demo.model.User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }
}
