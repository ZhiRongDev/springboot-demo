package com.example.demo.dto;

/**
 * LoginResponse is returned by POST /api/auth/login on success.
 *
 * A Java record — immutable, auto-generates all boilerplate.
 * Returns a UUID token and the authenticated user's ID.
 *
 * Example: {"token": "550e8400-e29b-41d4-a716-446655440000", "userId": 1}
 */
public record LoginResponse(String token, Long userId) {}
