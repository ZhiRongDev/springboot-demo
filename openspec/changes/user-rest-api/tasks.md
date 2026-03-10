## 1. Project Setup

- [ ] 1.1 Add `spring-boot-starter-validation` dependency to `pom.xml`
- [ ] 1.2 Create package structure: `controller`, `service`, `repository`, `model`, `dto`, `exception`

## 2. Model & DTOs

- [ ] 2.1 Create `model/User.java` with fields: `id` (Long), `name`, `email`, `passwordHash`
- [ ] 2.2 Create `dto/RegisterRequest.java` with `@NotBlank` on name, `@Email` on email, `@Size(min=6)` on password
- [ ] 2.3 Create `dto/LoginRequest.java` with `@NotBlank` on email and password
- [ ] 2.4 Create `dto/UpdateUserRequest.java` with optional name and email fields and validation annotations
- [ ] 2.5 Create `dto/UserResponse.java` with `id`, `name`, `email` (no password field)

## 3. Repository Layer

- [ ] 3.1 Create `repository/UserRepository.java` annotated with `@Repository`, backed by `ConcurrentHashMap<Long, User>`
- [ ] 3.2 Implement `save(User user)` — auto-increments ID using `AtomicLong`
- [ ] 3.3 Implement `findById(Long id)` returning `Optional<User>`
- [ ] 3.4 Implement `findByEmail(String email)` returning `Optional<User>`
- [ ] 3.5 Implement `deleteById(Long id)` returning `boolean`
- [ ] 3.6 Implement `existsByEmail(String email)` returning `boolean`

## 4. Custom Exceptions

- [ ] 4.1 Create `exception/UserNotFoundException.java` extending `RuntimeException`
- [ ] 4.2 Create `exception/EmailAlreadyExistsException.java` extending `RuntimeException`
- [ ] 4.3 Create `exception/GlobalExceptionHandler.java` annotated with `@RestControllerAdvice`
- [ ] 4.4 Add handler method for `UserNotFoundException` → 404
- [ ] 4.5 Add handler method for `EmailAlreadyExistsException` → 409
- [ ] 4.6 Add handler method for `MethodArgumentNotValidException` → 400 with field-level errors

## 5. Service Layer

- [ ] 5.1 Create `service/UserService.java` annotated with `@Service`, inject `UserRepository` via constructor
- [ ] 5.2 Declare `BCryptPasswordEncoder` as a `@Bean` in `DemoApplication` (or a `@Configuration` class)
- [ ] 5.3 Inject `PasswordEncoder` into `UserService` via constructor
- [ ] 5.4 Implement `register(RegisterRequest req)` — check email uniqueness, hash password, save, return `UserResponse`
- [ ] 5.5 Implement `login(LoginRequest req)` — find user by email, verify password hash, generate UUID token, return token + userId
- [ ] 5.6 Implement `getUserById(Long id)` — find user or throw `UserNotFoundException`, return `UserResponse`
- [ ] 5.7 Implement `updateUser(Long id, UpdateUserRequest req)` — validate email uniqueness, update fields, return `UserResponse`
- [ ] 5.8 Implement `deleteUser(Long id)` — verify exists, delete, return void

## 6. Controller Layer

- [ ] 6.1 Create `controller/UserController.java` annotated with `@RestController` and `@RequestMapping("/api")`
- [ ] 6.2 Inject `UserService` via constructor injection
- [ ] 6.3 Implement `POST /api/auth/register` — `@Valid` on request body, return `ResponseEntity<UserResponse>` with 201
- [ ] 6.4 Implement `POST /api/auth/login` — `@Valid` on request body, return 200 with token
- [ ] 6.5 Implement `GET /api/users/{id}` — `@PathVariable Long id`, return 200 with `UserResponse`
- [ ] 6.6 Implement `PUT /api/users/{id}` — `@Valid` on request body, return 200 with updated `UserResponse`
- [ ] 6.7 Implement `DELETE /api/users/{id}` — return `ResponseEntity<Void>` with 204

## 7. Verification

- [ ] 7.1 Start the application and confirm it runs without errors (`./mvnw spring-boot:run`)
- [ ] 7.2 Test `POST /api/auth/register` with a valid payload — confirm 201 response
- [ ] 7.3 Test `POST /api/auth/register` with duplicate email — confirm 409
- [ ] 7.4 Test `POST /api/auth/login` with correct credentials — confirm 200 + token
- [ ] 7.5 Test `POST /api/auth/login` with wrong password — confirm 401
- [ ] 7.6 Test `GET /api/users/{id}` with valid ID — confirm 200 + user data
- [ ] 7.7 Test `GET /api/users/999` — confirm 404
- [ ] 7.8 Test `PUT /api/users/{id}` with valid update — confirm 200 + updated fields
- [ ] 7.9 Test `DELETE /api/users/{id}` — confirm 204, then GET confirms 404
