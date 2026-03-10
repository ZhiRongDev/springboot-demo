## ADDED Requirements

### Requirement: Validation and error handling tutorial
The project SHALL include `docs/05-validation-and-errors.md` that explains Bean Validation, the `@Valid` annotation, and centralized error handling with `@RestControllerAdvice`.

#### Scenario: Bean Validation annotations
- **WHEN** a learner reads the tutorial
- **THEN** it SHALL explain `@NotBlank`, `@NotNull`, `@Email`, `@Size`, and `@Min` with examples from the DTO classes, and explain that these are standard JSR-380 annotations (not Spring-specific)

#### Scenario: @Valid trigger
- **WHEN** a learner reads the tutorial
- **THEN** it SHALL explain that placing `@Valid` on a `@RequestBody` parameter tells Spring MVC to validate the object before the controller method body runs, and what happens when validation fails (Spring throws `MethodArgumentNotValidException`)

#### Scenario: @RestControllerAdvice explanation
- **WHEN** a learner reads the tutorial
- **THEN** it SHALL explain `@RestControllerAdvice` as a global interceptor for exceptions thrown by any controller, show the `GlobalExceptionHandler` implementation with annotations and code comments, and explain `@ExceptionHandler`

#### Scenario: Consistent error response shape
- **WHEN** a learner reads the tutorial
- **THEN** it SHALL explain why returning a consistent JSON error body (with `status`, `message`, and optional `fieldErrors`) matters for API consumers, and show the error response structure used in this project

#### Scenario: TL;DR summary
- **WHEN** a learner opens the file
- **THEN** the first section SHALL be a concise TL;DR (3-5 bullet points) summarizing the key takeaways
