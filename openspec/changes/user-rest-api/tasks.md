## 1. Project Setup

- [x] 1.1 Add `spring-boot-starter-validation` dependency to `pom.xml`
- [x] 1.2 Create package structure: `controller`, `service`, `repository`, `model`, `dto`, `exception`

## 2. Model & DTOs

- [x] 2.1 Create `model/User.java` with fields: `id` (Long), `name`, `email`, `passwordHash`
- [x] 2.2 Create `dto/RegisterRequest.java` with `@NotBlank` on name, `@Email` on email, `@Size(min=6)` on password
- [x] 2.3 Create `dto/LoginRequest.java` with `@NotBlank` on email and password
- [x] 2.4 Create `dto/UpdateUserRequest.java` with optional name and email fields and validation annotations
- [x] 2.5 Create `dto/UserResponse.java` with `id`, `name`, `email` (no password field)

## 3. Repository Layer

- [x] 3.1 Create `repository/UserRepository.java` annotated with `@Repository`, backed by `ConcurrentHashMap<Long, User>`
- [x] 3.2 Implement `save(User user)` — auto-increments ID using `AtomicLong`
- [x] 3.3 Implement `findById(Long id)` returning `Optional<User>`
- [x] 3.4 Implement `findByEmail(String email)` returning `Optional<User>`
- [x] 3.5 Implement `deleteById(Long id)` returning `boolean`
- [x] 3.6 Implement `existsByEmail(String email)` returning `boolean`

## 4. Custom Exceptions

- [x] 4.1 Create `exception/UserNotFoundException.java` extending `RuntimeException`
- [x] 4.2 Create `exception/EmailAlreadyExistsException.java` extending `RuntimeException`
- [x] 4.3 Create `exception/GlobalExceptionHandler.java` annotated with `@RestControllerAdvice`
- [x] 4.4 Add handler method for `UserNotFoundException` → 404
- [x] 4.5 Add handler method for `EmailAlreadyExistsException` → 409
- [x] 4.6 Add handler method for `MethodArgumentNotValidException` → 400 with field-level errors

## 5. Service Layer

- [x] 5.1 Create `service/UserService.java` annotated with `@Service`, inject `UserRepository` via constructor
- [x] 5.2 Declare `BCryptPasswordEncoder` as a `@Bean` in `DemoApplication` (or a `@Configuration` class)
- [x] 5.3 Inject `PasswordEncoder` into `UserService` via constructor
- [x] 5.4 Implement `register(RegisterRequest req)` — check email uniqueness, hash password, save, return `UserResponse`
- [x] 5.5 Implement `login(LoginRequest req)` — find user by email, verify password hash, generate UUID token, return token + userId
- [x] 5.6 Implement `getUserById(Long id)` — find user or throw `UserNotFoundException`, return `UserResponse`
- [x] 5.7 Implement `updateUser(Long id, UpdateUserRequest req)` — validate email uniqueness, update fields, return `UserResponse`
- [x] 5.8 Implement `deleteUser(Long id)` — verify exists, delete, return void

## 6. Controller Layer

- [x] 6.1 Create `controller/UserController.java` annotated with `@RestController` and `@RequestMapping("/api")`
- [x] 6.2 Inject `UserService` via constructor injection
- [x] 6.3 Implement `POST /api/auth/register` — `@Valid` on request body, return `ResponseEntity<UserResponse>` with 201
- [x] 6.4 Implement `POST /api/auth/login` — `@Valid` on request body, return 200 with token
- [x] 6.5 Implement `GET /api/users/{id}` — `@PathVariable Long id`, return 200 with `UserResponse`
- [x] 6.6 Implement `PUT /api/users/{id}` — `@Valid` on request body, return 200 with updated `UserResponse`
- [x] 6.7 Implement `DELETE /api/users/{id}` — return `ResponseEntity<Void>` with 204

## 7. Verification

- [x] 7.1 Start the application and confirm it runs without errors (`./mvnw spring-boot:run`)
- [x] 7.2 Test `POST /api/auth/register` with a valid payload — confirm 201 response
- [x] 7.3 Test `POST /api/auth/register` with duplicate email — confirm 409
- [x] 7.4 Test `POST /api/auth/login` with correct credentials — confirm 200 + token
- [x] 7.5 Test `POST /api/auth/login` with wrong password — confirm 401
- [x] 7.6 Test `GET /api/users/{id}` with valid ID — confirm 200 + user data
- [x] 7.7 Test `GET /api/users/999` — confirm 404
- [x] 7.8 Test `PUT /api/users/{id}` with valid update — confirm 200 + updated fields
- [x] 7.9 Test `DELETE /api/users/{id}` — confirm 204, then GET confirms 404
