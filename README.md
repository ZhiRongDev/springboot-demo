# springboot-demo

A Spring Boot learning project that implements a simple **User Management REST API**.
Built to demonstrate core Spring Boot concepts: dependency injection, beans, layered architecture, validation, and global error handling.

---

## What's Inside

| Layer | Class | Role |
|---|---|---|
| HTTP | `UserController` | Receives requests, returns responses |
| Business | `UserService` | Business rules, password hashing, token generation |
| Data | `UserRepository` | In-memory storage (`ConcurrentHashMap`) |

```
src/main/java/com/example/demo/
├── DemoApplication.java          ← entry point + @Bean PasswordEncoder
├── controller/UserController.java
├── service/UserService.java
├── repository/UserRepository.java
├── model/User.java
├── dto/                          ← RegisterRequest, LoginRequest, UpdateUserRequest, UserResponse, LoginResponse
└── exception/                    ← UserNotFoundException, EmailAlreadyExistsException, GlobalExceptionHandler
```

---

## API Endpoints

| Method | Path | Description | Success |
|---|---|---|---|
| `POST` | `/api/auth/register` | Create a new user | `201 Created` |
| `POST` | `/api/auth/login` | Authenticate, get token | `200 OK` |
| `GET` | `/api/users/{id}` | Get user profile | `200 OK` |
| `PUT` | `/api/users/{id}` | Update name / email | `200 OK` |
| `DELETE` | `/api/users/{id}` | Delete a user | `204 No Content` |

### Register

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name":"Alice","email":"alice@example.com","password":"secret123"}'
```

```json
// 201 Created
{"id": 1, "name": "Alice", "email": "alice@example.com"}
```

### Login

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"alice@example.com","password":"secret123"}'
```

```json
// 200 OK
{"token": "550e8400-e29b-41d4-a716-446655440000", "userId": 1}
```

### Get User

```bash
curl http://localhost:8080/api/users/1
```

```json
// 200 OK
{"id": 1, "name": "Alice", "email": "alice@example.com"}
```

### Update User

```bash
curl -X PUT http://localhost:8080/api/users/1 \
  -H "Content-Type: application/json" \
  -d '{"name":"Alice Smith"}'
```

### Delete User

```bash
curl -X DELETE http://localhost:8080/api/users/1
# 204 No Content
```

---

## Error Responses

| Scenario | HTTP Status | Body |
|---|---|---|
| User not found | `404` | `{"error": "User not found with id: 99"}` |
| Email taken | `409` | `{"error": "Email already in use: ..."}` |
| Wrong password | `401` | `{"error": "Invalid email or password"}` |
| Validation failure | `400` | `{"error": "Validation failed", "fieldErrors": {...}}` |

---

## Prerequisites

- Java 21
- Maven (or use the included `./mvnw` wrapper — no install needed)

---

## How to Start

```bash
# Clone or enter the project directory
cd springboot-demo

# Run with the Maven wrapper (downloads dependencies automatically on first run)
./mvnw spring-boot:run
```

The server starts on **http://localhost:8080**.

To stop it: `Ctrl + C`

### Build a JAR (optional)

```bash
./mvnw clean package
java -jar target/springboot-demo-0.0.1-SNAPSHOT.jar
```

---

## Key Spring Boot Concepts Demonstrated

| Concept | Where to look |
|---|---|
| `@SpringBootApplication` | `DemoApplication.java` |
| `@Bean` / `@Configuration` | `DemoApplication.java` — `PasswordEncoder` bean |
| `@RestController` + routing | `UserController.java` |
| `@Service` (business layer) | `UserService.java` |
| `@Repository` (data layer) | `UserRepository.java` |
| Constructor injection (DI) | `UserService`, `UserController` constructors |
| `@Valid` + Bean Validation | `RegisterRequest.java`, controller `@RequestBody` |
| `@RestControllerAdvice` | `GlobalExceptionHandler.java` |

> Full tutorial docs covering these concepts in detail are planned in `docs/` — see `openspec/changes/springboot-tutorial/`.

---

## Tech Stack

- **Spring Boot** 4.0.3
- **Java** 21
- **spring-boot-starter-webmvc** — REST API support
- **spring-boot-starter-validation** — Bean Validation (JSR-380)
- **spring-security-crypto** — BCrypt password hashing
- **Storage** — In-memory `ConcurrentHashMap` (no database required)
