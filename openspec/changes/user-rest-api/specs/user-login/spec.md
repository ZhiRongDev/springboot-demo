## ADDED Requirements

### Requirement: Authenticate an existing user
The system SHALL provide a `POST /api/auth/login` endpoint that accepts an email and password, verifies the credentials, and returns an auth token on success.

#### Scenario: Successful login
- **WHEN** a client sends `POST /api/auth/login` with the correct email and password for an existing user
- **THEN** the system SHALL return HTTP 200 with a JSON body containing a `token` string (UUID format) and the user's `id`

#### Scenario: Wrong password
- **WHEN** a client sends `POST /api/auth/login` with a valid email but an incorrect password
- **THEN** the system SHALL return HTTP 401 Unauthorized with an error message indicating invalid credentials

#### Scenario: Email not found
- **WHEN** a client sends `POST /api/auth/login` with an email that does not match any registered user
- **THEN** the system SHALL return HTTP 401 Unauthorized (same message as wrong password to avoid user enumeration)

#### Scenario: Missing credentials
- **WHEN** a client sends `POST /api/auth/login` with a missing or blank email or password
- **THEN** the system SHALL return HTTP 400 Bad Request with validation error details
