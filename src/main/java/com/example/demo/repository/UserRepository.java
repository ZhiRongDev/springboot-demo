package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * UserRepository is the DATA ACCESS LAYER.
 *
 * @Repository — This is a Spring stereotype annotation. It does two things:
 *   1. Marks this class as a Spring-managed bean (same effect as @Component).
 *   2. Enables Spring's persistence exception translation — if this were a real
 *      database repository, Spring would wrap low-level SQL exceptions into its own
 *      DataAccessException hierarchy. Here it's cosmetic but teaches the pattern.
 *
 * In a real app you would extend JpaRepository<User, Long> and get all these
 * methods for free. We implement them manually to understand what's happening.
 *
 * Storage:
 *   ConcurrentHashMap — thread-safe HashMap. Multiple HTTP requests can hit the
 *   server concurrently; a plain HashMap would cause data corruption. ConcurrentHashMap
 *   handles concurrent reads/writes safely.
 *
 *   AtomicLong — thread-safe counter for auto-incrementing IDs. incrementAndGet()
 *   is an atomic operation, so two concurrent registrations can't get the same ID.
 *
 * Dependency injection note:
 *   UserService depends on UserRepository. Spring sees that UserService's constructor
 *   takes a UserRepository parameter, finds this @Repository bean, and injects it.
 *   UserRepository itself has no dependencies, so Spring creates it first.
 */
@Repository
public class UserRepository {

    // The in-memory "database": userId → User
    private final ConcurrentHashMap<Long, User> storage = new ConcurrentHashMap<>();

    // Auto-increment counter starting at 1 (mimics a DB sequence/auto-increment column)
    private final AtomicLong idCounter = new AtomicLong(1);

    /**
     * Save a user. If the user has no ID yet, assigns one (insert).
     * If the user already has an ID, overwrites the existing entry (update).
     *
     * Returns the saved user (with ID populated).
     */
    public User save(User user) {
        if (user.getId() == null) {
            // getAndIncrement() atomically returns current value then increments
            user.setId(idCounter.getAndIncrement());
        }
        storage.put(user.getId(), user);
        return user;
    }

    /**
     * Find a user by their ID.
     *
     * Returns Optional<User> instead of User to force the caller to handle
     * the "not found" case explicitly. Optional.ofNullable wraps null → empty Optional.
     */
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    /**
     * Find a user by email address (case-insensitive).
     *
     * .stream() creates a Java Stream over all stored users.
     * .filter() keeps only users whose email matches.
     * .findFirst() returns the first match as an Optional.
     */
    public Optional<User> findByEmail(String email) {
        return storage.values().stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    /**
     * Delete a user by ID.
     *
     * ConcurrentHashMap.remove() returns the removed value (or null if not found).
     * We use that to return true (deleted) / false (not found).
     */
    public boolean deleteById(Long id) {
        return storage.remove(id) != null;
    }

    /**
     * Check whether an email is already registered.
     *
     * .anyMatch() is a terminal stream operation that short-circuits on first match —
     * more efficient than collecting all matches.
     */
    public boolean existsByEmail(String email) {
        return storage.values().stream()
                .anyMatch(user -> user.getEmail().equalsIgnoreCase(email));
    }
}
