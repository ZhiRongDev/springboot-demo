## ADDED Requirements

### Requirement: Dependency injection tutorial
The project SHALL include `docs/02-dependency-injection.md` that teaches Inversion of Control (IoC) and Dependency Injection (DI) as the central mechanism of the Spring container.

#### Scenario: IoC concept explanation
- **WHEN** a learner reads the tutorial
- **THEN** it SHALL explain the "Hollywood Principle" ("Don't call us, we'll call you") and contrast how objects are created in plain Java vs in Spring

#### Scenario: Constructor injection vs field injection
- **WHEN** a learner reads the tutorial
- **THEN** it SHALL show side-by-side code examples of field injection (`@Autowired` on field) vs constructor injection, explain why constructor injection is preferred, and reference the pattern used in `UserController`

#### Scenario: ApplicationContext explanation
- **WHEN** a learner reads the tutorial
- **THEN** it SHALL explain the `ApplicationContext` as the Spring IoC container that owns all beans and resolves dependencies at startup

#### Scenario: TL;DR summary
- **WHEN** a learner opens the file
- **THEN** the first section SHALL be a concise TL;DR (3-5 bullet points) summarizing the key takeaways
