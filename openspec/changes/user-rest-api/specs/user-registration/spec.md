## ADDED Requirements

### Requirement: Register a new user account
The system SHALL provide a `POST /api/auth/register` endpoint that accepts a name, email, and password, validates the input, and creates a new user account.

#### Scenario: Successful registration
- **WHEN** a client sends `POST /api/auth/register` with a valid name, email, and password
- **THEN** the system SHALL create the user, return HTTP 201, and include the new user's id, name, and email in the response body (password SHALL NOT be returned)

#### Scenario: Email already in use
- **WHEN** a client sends `POST /api/auth/register` with an email that belongs to an existing user
- **THEN** the system SHALL return HTTP 409 Conflict with an error message indicating the email is taken

#### Scenario: Missing required fields
- **WHEN** a client sends `POST /api/auth/register` with a missing or blank name, email, or password
- **THEN** the system SHALL return HTTP 400 Bad Request with validation error details for each failing field

#### Scenario: Invalid email format
- **WHEN** a client sends `POST /api/auth/register` with a string that is not a valid email address
- **THEN** the system SHALL return HTTP 400 Bad Request with a message indicating the email format is invalid

#### Scenario: Password too short
- **WHEN** a client sends `POST /api/auth/register` with a password shorter than 6 characters
- **THEN** the system SHALL return HTTP 400 Bad Request with a message indicating the password minimum length requirement
