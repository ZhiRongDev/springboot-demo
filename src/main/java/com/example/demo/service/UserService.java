package com.example.demo.service;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.dto.UpdateUserRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.exception.EmailAlreadyExistsException;
import com.example.demo.exception.InvalidCredentialsException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * UserService is the BUSINESS LOGIC LAYER.
 *
 * @Service — a Spring stereotype annotation. It marks this class as a Spring-managed
 * bean responsible for business operations. Functionally identical to @Component,
 * but the name communicates intent to other developers.
 *
 * ─── Constructor Injection ───────────────────────────────────────────────────
 * Notice: there is no @Autowired annotation on the constructor. Since Spring 4.3,
 * if a class has only ONE constructor, Spring automatically uses it for injection.
 * You can add @Autowired explicitly — it's just not required.
 *
 * The constructor declares two dependencies:
 *   - UserRepository   → Spring finds the @Repository bean and injects it.
 *   - PasswordEncoder  → Spring finds the @Bean declared in DemoApplication and injects it.
 *
 * Both fields are `final` — a benefit of constructor injection. Once set in the
 * constructor, they can never be reassigned. This makes the service immutable and
 * easier to test (you can pass mock objects in unit tests directly via the constructor).
 *
 * CONTRAST — field injection (what NOT to do):
 *   @Autowired
 *   private UserRepository userRepository;   // mutable, harder to test, less explicit
 *
 * ─── Responsibilities ────────────────────────────────────────────────────────
 * This layer handles:
 *   - Business rules: "email must be unique before saving"
 *   - Orchestration: "hash password, THEN save user, THEN return response DTO"
 *   - Mapping: converting between domain objects (User) and DTOs (UserResponse)
 *
 * The controller knows nothing about hashing or ID generation.
 * The repository knows nothing about business rules.
 * Clean separation of concerns.
 */
@Service
public class UserService {

    // final = immutable after construction; dependency injected via constructor
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // In-memory token store: token (UUID string) → userId
    // In production this would be a distributed cache like Redis.
    private final Map<String, Long> tokenStore = new ConcurrentHashMap<>();

    // Spring calls this constructor and injects the matching beans automatically.
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ─── Register ────────────────────────────────────────────────────────────

    /**
     * Registers a new user.
     *
     * Flow:
     *   1. Check email uniqueness (business rule).
     *   2. Hash the password with BCrypt.
     *   3. Persist the new User via the repository.
     *   4. Map to UserResponse (strips out passwordHash) and return.
     *
     * Throws EmailAlreadyExistsException (→ 409) if the email is taken.
     */
    public UserResponse register(RegisterRequest req) {
        // Business rule: email must be unique
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new EmailAlreadyExistsException(req.getEmail());
        }

        User user = new User();
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        // encode() hashes the plain-text password. We NEVER store plain-text passwords.
        user.setPasswordHash(passwordEncoder.encode(req.getPassword()));

        User saved = userRepository.save(user);

        // UserResponse.from() converts the internal User to the public-facing DTO
        return UserResponse.from(saved);
    }

    // ─── Login ───────────────────────────────────────────────────────────────

    /**
     * Authenticates a user and issues a token.
     *
     * Security note: we throw the same exception for "email not found" and
     * "wrong password". This prevents user enumeration attacks.
     *
     * Returns a LoginResponse with a UUID token and the user's ID.
     */
    public LoginResponse login(LoginRequest req) {
        // orElseThrow: if findByEmail returns empty Optional, throw the exception
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(InvalidCredentialsException::new);

        // matches(plainText, encodedHash) — BCrypt extracts the salt from the hash
        // and re-hashes the plain text to compare. Returns true if they match.
        if (!passwordEncoder.matches(req.getPassword(), user.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }

        // Generate a random UUID as the session token
        String token = UUID.randomUUID().toString();
        tokenStore.put(token, user.getId());

        return new LoginResponse(token, user.getId());
    }

    // ─── Get User ────────────────────────────────────────────────────────────

    /**
     * Returns a user's public profile.
     *
     * Throws UserNotFoundException (→ 404) if no user exists for the given ID.
     */
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return UserResponse.from(user);
    }

    // ─── Update User ─────────────────────────────────────────────────────────

    /**
     * Updates a user's name and/or email.
     *
     * Both fields are optional — the client may send just one.
     * If neither is provided, we reject with IllegalArgumentException (→ 400).
     * If the new email is already taken by a DIFFERENT user, we reject with 409.
     */
    public UserResponse updateUser(Long id, UpdateUserRequest req) {
        // Reject empty updates
        if (req.getName() == null && req.getEmail() == null) {
            throw new IllegalArgumentException("At least one field (name or email) must be provided");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        // If a new email is provided, ensure it's not taken by someone else
        if (req.getEmail() != null) {
            userRepository.findByEmail(req.getEmail())
                    .filter(existing -> !existing.getId().equals(id)) // same user is fine
                    .ifPresent(existing -> {
                        throw new EmailAlreadyExistsException(req.getEmail());
                    });
            user.setEmail(req.getEmail());
        }

        if (req.getName() != null) {
            user.setName(req.getName());
        }

        return UserResponse.from(userRepository.save(user));
    }

    // ─── Delete User ─────────────────────────────────────────────────────────

    /**
     * Deletes a user by ID.
     *
     * Throws UserNotFoundException (→ 404) if the user doesn't exist.
     */
    public void deleteUser(Long id) {
        boolean deleted = userRepository.deleteById(id);
        if (!deleted) {
            throw new UserNotFoundException(id);
        }
    }
}
