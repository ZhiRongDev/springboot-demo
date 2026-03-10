## ADDED Requirements

### Requirement: Delete a user account
The system SHALL provide a `DELETE /api/users/{id}` endpoint that permanently removes a user account identified by their numeric ID.

#### Scenario: Successful deletion
- **WHEN** a client sends `DELETE /api/users/{id}` with a valid existing user ID
- **THEN** the system SHALL remove the user and return HTTP 204 No Content with an empty body

#### Scenario: User not found
- **WHEN** a client sends `DELETE /api/users/{id}` with an ID that does not match any user
- **THEN** the system SHALL return HTTP 404 Not Found with an error message indicating the user does not exist

#### Scenario: Invalid ID format
- **WHEN** a client sends `DELETE /api/users/{id}` with a non-numeric ID
- **THEN** the system SHALL return HTTP 400 Bad Request
