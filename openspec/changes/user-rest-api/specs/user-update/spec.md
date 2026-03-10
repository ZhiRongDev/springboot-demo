## ADDED Requirements

### Requirement: Update a user's profile
The system SHALL provide a `PUT /api/users/{id}` endpoint that updates the name and/or email of an existing user.

#### Scenario: Successful update
- **WHEN** a client sends `PUT /api/users/{id}` with a valid user ID and a JSON body containing at least one of `name` or `email`
- **THEN** the system SHALL update the user's fields and return HTTP 200 with the updated user's `id`, `name`, and `email`

#### Scenario: User not found
- **WHEN** a client sends `PUT /api/users/{id}` with an ID that does not match any user
- **THEN** the system SHALL return HTTP 404 Not Found with an appropriate error message

#### Scenario: New email already taken
- **WHEN** a client sends `PUT /api/users/{id}` with an email that belongs to a different existing user
- **THEN** the system SHALL return HTTP 409 Conflict with an error message indicating the email is already in use

#### Scenario: Invalid fields
- **WHEN** a client sends `PUT /api/users/{id}` with a blank name or an invalid email format
- **THEN** the system SHALL return HTTP 400 Bad Request with field-level validation error details

#### Scenario: Empty body
- **WHEN** a client sends `PUT /api/users/{id}` with an empty JSON body `{}`
- **THEN** the system SHALL return HTTP 400 Bad Request indicating that at least one field must be provided
