package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * RegisterRequest is a DTO (Data Transfer Object) — it represents the JSON body
 * that a client sends when calling POST /api/auth/register.
 *
 * Why use a DTO instead of the User model directly?
 *   1. The client provides a plain-text password; the User model stores a hash.
 *   2. We don't want clients to set their own ID or other internal fields.
 *   3. Validation annotations belong on the API contract, not the domain model.
 *
 * Bean Validation annotations (JSR-380) — these are checked when @Valid is placed
 * on the @RequestBody parameter in the controller. Spring throws
 * MethodArgumentNotValidException automatically if any constraint fails.
 */
public class RegisterRequest {

    // @NotBlank: rejects null, empty string "", or whitespace-only "   "
    @NotBlank(message = "Name is required")
    private String name;

    // @NotBlank ensures the field is present; @Email checks format (e.g. user@example.com)
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    // @Size(min=6) rejects passwords shorter than 6 characters
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    // --- Getters and Setters ---
    // Jackson (the JSON library Spring Boot uses) needs setters to deserialize
    // the incoming JSON body into this object.

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
