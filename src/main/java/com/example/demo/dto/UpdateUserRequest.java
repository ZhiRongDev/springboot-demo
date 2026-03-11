package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

/**
 * UpdateUserRequest is the DTO for PUT /api/users/{id}.
 *
 * Both fields are optional — the client may send one or both.
 * If a field IS provided, it must still be valid:
 *   - name: cannot be blank (we use @Size(min=1) instead of @NotBlank so null is allowed)
 *   - email: must be a valid email format if provided
 *
 * The service layer checks that at least one field is non-null (not an empty update).
 */
public class UpdateUserRequest {

    // @Size(min=1) rejects empty string "" but allows null (field omitted in JSON)
    @Size(min = 1, message = "Name must not be blank")
    private String name;

    // @Email validates format only when the value is non-null
    @Email(message = "Invalid email format")
    private String email;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
