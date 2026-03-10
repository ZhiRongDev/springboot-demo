## ADDED Requirements

### Requirement: Layered architecture tutorial
The project SHALL include `docs/04-layered-architecture.md` that walks through the Controller → Service → Repository layers as implemented in `user-rest-api`, showing how each layer is wired to the next via constructor injection.

#### Scenario: Request lifecycle walkthrough
- **WHEN** a learner reads the tutorial
- **THEN** it SHALL trace a single HTTP request (e.g., `POST /api/auth/register`) step by step from the `DispatcherServlet` through `UserController` → `UserService` → `UserRepository` and back, with an ASCII diagram

#### Scenario: Controller layer explanation
- **WHEN** a learner reads the tutorial
- **THEN** it SHALL explain `@RestController`, `@RequestMapping`, `@PostMapping`, `@GetMapping`, `@PathVariable`, `@RequestBody`, and `ResponseEntity` with annotated code from `UserController.java`

#### Scenario: Service layer explanation
- **WHEN** a learner reads the tutorial
- **THEN** it SHALL explain why business logic belongs in `@Service` (not the controller), and show how `UserService` depends on `UserRepository` via constructor injection

#### Scenario: Repository layer explanation
- **WHEN** a learner reads the tutorial
- **THEN** it SHALL explain `@Repository`, what it does beyond `@Component` (exception translation), and how in-memory storage with `ConcurrentHashMap` mimics a real data access layer

#### Scenario: TL;DR summary
- **WHEN** a learner opens the file
- **THEN** the first section SHALL be a concise TL;DR (3-5 bullet points) summarizing the key takeaways
