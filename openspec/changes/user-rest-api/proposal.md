## Why

This project is a Spring Boot learning exercise. We need a foundational set of User management REST APIs to demonstrate the core Spring Boot concepts: controllers, services, repositories, dependency injection, beans, and data validation. Building these APIs provides a concrete, hands-on context for understanding the Spring ecosystem.

## What Changes

- Add `POST /api/auth/register` — create a new user account
- Add `POST /api/auth/login` — authenticate and return a token
- Add `GET /api/users/{id}` — retrieve a user's profile
- Add `PUT /api/users/{id}` — update a user's profile
- Add `DELETE /api/users/{id}` — remove a user account
- Add in-memory user storage (no database required for learning purposes)
- Add request/response DTOs with validation annotations
- Add a basic password hashing utility

## Capabilities

### New Capabilities

- `user-registration`: Accepts name, email, and password; validates input; stores user; returns created user info
- `user-login`: Accepts email and password; verifies credentials; returns a simple auth token (UUID-based for simplicity)
- `user-profile`: Fetch a user by ID; returns public profile data
- `user-update`: Update name and/or email for an existing user by ID
- `user-delete`: Remove a user by ID

### Modified Capabilities

<!-- None — this is a greenfield feature set -->

## Impact

- New package structure under `com.example.demo`:
  - `controller/` — REST controllers
  - `service/` — business logic
  - `repository/` — data access layer (in-memory `HashMap`)
  - `model/` — User entity
  - `dto/` — request and response objects
  - `exception/` — custom exceptions and global error handler
- Requires adding `spring-boot-starter-validation` to `pom.xml`
- No database dependency — uses in-memory storage to keep setup simple for learning
