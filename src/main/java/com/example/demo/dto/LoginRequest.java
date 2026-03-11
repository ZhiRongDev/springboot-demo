package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * LoginRequest is the DTO for POST /api/auth/login.
 *
 * Intentionally minimal: email and password only.
 * The controller puts @Valid on this parameter so Spring auto-validates it
 * before the method body runs.
 */
public class LoginRequest {

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
