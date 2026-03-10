## ADDED Requirements

### Requirement: Retrieve a user's profile
The system SHALL provide a `GET /api/users/{id}` endpoint that returns the public profile of a user identified by their numeric ID.

#### Scenario: User found
- **WHEN** a client sends `GET /api/users/{id}` with a valid existing user ID
- **THEN** the system SHALL return HTTP 200 with a JSON body containing the user's `id`, `name`, and `email` (password SHALL NOT be returned)

#### Scenario: User not found
- **WHEN** a client sends `GET /api/users/{id}` with an ID that does not match any user
- **THEN** the system SHALL return HTTP 404 Not Found with an error message indicating no user exists for that ID

#### Scenario: Invalid ID format
- **WHEN** a client sends `GET /api/users/{id}` with a non-numeric ID (e.g., `/api/users/abc`)
- **THEN** the system SHALL return HTTP 400 Bad Request
