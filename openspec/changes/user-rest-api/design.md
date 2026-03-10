## Context

This is a learning project using Spring Boot 4.x and Java 21. The goal is to build simple, readable User management REST APIs that clearly demonstrate Spring Boot's key patterns: layered architecture, dependency injection, beans, and validation. There is no existing user management code — this is built from scratch.

## Goals / Non-Goals

**Goals:**
- Implement a clean 3-layer architecture: Controller → Service → Repository
- Use Spring's dependency injection (`@Autowired` / constructor injection) throughout
- Demonstrate `@RestController`, `@Service`, `@Repository` stereotypes as Spring-managed beans
- Use `@Valid` + Bean Validation annotations on DTOs
- Provide a global exception handler using `@ControllerAdvice`
- Keep storage in-memory (`HashMap`) to avoid database setup complexity

**Non-Goals:**
- JWT / OAuth2 security — a UUID token is sufficient for learning purposes
- Database persistence (JPA / Hibernate)
- Password salting beyond basic BCrypt-style hashing (using Spring Security's `BCryptPasswordEncoder`)
- Pagination or filtering of users
- Role-based access control

## Decisions

### 1. Layered architecture (Controller → Service → Repository)

**Decision**: Three distinct layers with clear responsibilities.

- `UserController` handles HTTP concerns only (mapping, request parsing, response codes).
- `UserService` contains business logic (validation, password hashing, token generation).
- `UserRepository` handles data access (in-memory `HashMap<Long, User>`).

**Why**: This is the idiomatic Spring pattern. It makes dependency injection tangible — each layer depends on the interface/class below it, injected by Spring's IoC container.

**Alternatives considered**: Putting all logic in the controller (anemic architecture) — rejected because it defeats the learning purpose of showing DI and separation of concerns.

---

### 2. In-memory storage with `HashMap`

**Decision**: Use a `HashMap<Long, User>` inside `UserRepository`, annotated with `@Repository`.

**Why**: No database setup needed. The focus is on Spring concepts, not SQL. The `@Repository` annotation still teaches the concept of a Spring-managed data access bean even without JPA.

**Alternatives considered**: H2 in-memory database with JPA — valid but introduces too many concepts at once (entity mapping, `@Entity`, `JpaRepository`).

---

### 3. Constructor injection over field injection

**Decision**: Use constructor injection for all `@Autowired` dependencies.

**Why**: Constructor injection is the recommended best practice. It makes dependencies explicit, supports immutability (`final` fields), and makes unit testing easier. Field injection (`@Autowired` on fields) is shown in comments as a contrast.

---

### 4. DTOs for request and response

**Decision**: Separate `RegisterRequest`, `LoginRequest`, `UpdateUserRequest` (input) and `UserResponse` (output) records/classes from the `User` model.

**Why**: Never expose internal model objects directly over the API. DTOs decouple the API contract from persistence concerns and allow adding validation annotations cleanly.

---

### 5. Simple UUID token for login

**Decision**: On successful login, return a `UUID.randomUUID().toString()` as the auth token. Store it in a `HashMap<String, Long>` (token → userId) in the service layer.

**Why**: Keeps the implementation minimal while showing the concept of stateful token management. The focus is not on security but on request/response flow.

---

### 6. Global exception handling with `@ControllerAdvice`

**Decision**: Create `GlobalExceptionHandler` annotated with `@RestControllerAdvice` to catch `UserNotFoundException`, `EmailAlreadyExistsException`, and `MethodArgumentNotValidException`.

**Why**: Centralizes error handling. Teaches the `@ControllerAdvice` pattern and produces consistent JSON error responses.

## Risks / Trade-offs

- **In-memory storage is not thread-safe** → Use `ConcurrentHashMap` instead of `HashMap` to handle concurrent requests safely.
- **Passwords stored after BCrypt hash, but no salt rounds tuning** → Default BCrypt strength (10) is fine for a learning project.
- **UUID tokens never expire** → Acceptable for a tutorial; would need TTL in production.
- **No real authentication middleware** → The token endpoint exists for demonstration; endpoints are not actually protected by it.

## Package Structure

```
com.example.demo
├── DemoApplication.java          ← @SpringBootApplication entry point
├── controller/
│   └── UserController.java       ← @RestController, handles HTTP
├── service/
│   └── UserService.java          ← @Service, business logic
├── repository/
│   └── UserRepository.java       ← @Repository, in-memory storage
├── model/
│   └── User.java                 ← Plain Java object (no @Entity)
├── dto/
│   ├── RegisterRequest.java
│   ├── LoginRequest.java
│   ├── UpdateUserRequest.java
│   └── UserResponse.java
└── exception/
    ├── UserNotFoundException.java
    ├── EmailAlreadyExistsException.java
    └── GlobalExceptionHandler.java   ← @RestControllerAdvice
```
