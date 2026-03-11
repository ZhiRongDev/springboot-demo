package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.dto.UpdateUserRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController is the HTTP LAYER — the outermost layer of our 3-tier architecture.
 *
 * Its only job is to:
 *   1. Receive HTTP requests and extract data (path variables, request body).
 *   2. Delegate all work to UserService.
 *   3. Wrap the result in an appropriate HTTP response (status code + body).
 *
 * It knows NOTHING about password hashing, token generation, or data storage.
 * Those are UserService's concerns.
 *
 * ─── Key Annotations ─────────────────────────────────────────────────────────
 *
 * @RestController
 *   Combines @Controller + @ResponseBody.
 *   @Controller  → registers this class as a Spring MVC controller (handles HTTP).
 *   @ResponseBody → tells Spring to serialize the return value of every method to JSON
 *                   automatically (via Jackson). Without it, Spring would look for a
 *                   view template (like Thymeleaf/JSP) instead.
 *
 * @RequestMapping("/api")
 *   All routes in this controller are prefixed with /api.
 *   So @PostMapping("/auth/register") becomes POST /api/auth/register.
 *
 * ─── How Spring Routes Requests ──────────────────────────────────────────────
 * When a request arrives:
 *   1. Tomcat (embedded server) receives it.
 *   2. DispatcherServlet (Spring MVC's front controller) takes over.
 *   3. It consults a HandlerMapping to find which controller method matches the URL + method.
 *   4. It calls that method.
 *   5. The return value is serialized to JSON and sent back.
 *
 * ─── Constructor Injection ────────────────────────────────────────────────────
 * UserController depends on UserService. Spring injects the @Service bean automatically.
 * `private final` + constructor injection = immutable, testable controller.
 */
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    // Spring automatically injects the UserService bean here.
    // No @Autowired needed since there's only one constructor.
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ─── POST /api/auth/register ──────────────────────────────────────────────

    /**
     * Register a new user.
     *
     * @PostMapping("/auth/register") — handles HTTP POST requests to /api/auth/register.
     *
     * @RequestBody RegisterRequest req
     *   Tells Spring to deserialize the JSON request body into a RegisterRequest object.
     *   Jackson reads the JSON fields and maps them to the Java fields via setters.
     *
     * @Valid
     *   Triggers Bean Validation on the RegisterRequest object BEFORE this method runs.
     *   If any constraint fails (@NotBlank, @Email, @Size), Spring throws
     *   MethodArgumentNotValidException, which GlobalExceptionHandler maps to 400.
     *
     * ResponseEntity<UserResponse>
     *   Gives us full control over the HTTP response: status code + body.
     *   ResponseEntity.status(CREATED).body(response) → HTTP 201 + JSON body.
     */
    @PostMapping("/auth/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest req) {
        UserResponse response = userService.register(req);
        // HTTP 201 Created: the standard status code when a resource is successfully created
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ─── POST /api/auth/login ─────────────────────────────────────────────────

    /**
     * Authenticate a user and receive a token.
     *
     * Returns HTTP 200 OK with a JSON body: {"token": "...", "userId": 1}
     */
    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest req) {
        LoginResponse response = userService.login(req);
        return ResponseEntity.ok(response);  // ResponseEntity.ok() = HTTP 200
    }

    // ─── GET /api/users/{id} ──────────────────────────────────────────────────

    /**
     * Get a user's profile by ID.
     *
     * @GetMapping("/users/{id}") — {id} is a path variable placeholder.
     *
     * @PathVariable Long id
     *   Spring extracts the {id} segment from the URL and converts it to a Long.
     *   If the value can't be converted (e.g., /api/users/abc), Spring returns 400 automatically.
     *
     * Returns HTTP 200 with the UserResponse body.
     * If the user doesn't exist, GlobalExceptionHandler maps UserNotFoundException → 404.
     */
    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // ─── PUT /api/users/{id} ──────────────────────────────────────────────────

    /**
     * Update a user's name and/or email.
     *
     * @Valid validates the UpdateUserRequest body (e.g., @Email format check).
     * Both name and email are optional — the service rejects an empty request body.
     *
     * Returns HTTP 200 with the updated UserResponse.
     */
    @PutMapping("/users/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest req) {
        return ResponseEntity.ok(userService.updateUser(id, req));
    }

    // ─── DELETE /api/users/{id} ───────────────────────────────────────────────

    /**
     * Delete a user by ID.
     *
     * ResponseEntity<Void> — we return no body (Void), just an HTTP status.
     * HTTP 204 No Content is the correct status for a successful DELETE
     * that has no response body.
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        // .build() creates a ResponseEntity with no body
        return ResponseEntity.noContent().build();  // HTTP 204
    }
}
