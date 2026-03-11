package com.example.demo.model;

/**
 * User is our "domain model" — it represents the user as stored internally.
 *
 * Important: This is a plain Java class (POJO). It is NOT annotated with @Entity
 * because we're using in-memory storage instead of a database. In a real app with
 * JPA/Hibernate, you would add @Entity and @Id here.
 *
 * This class is NEVER sent directly over the API. We use UserResponse (a DTO)
 * to control what fields are exposed — for example, passwordHash is never returned.
 */
public class User {

    private Long id;
    private String name;
    private String email;

    // We store the BCrypt hash, never the plain-text password.
    private String passwordHash;

    // --- Getters and Setters ---
    // Spring's DI container, Jackson (JSON library), and our own code all need
    // to read/write these fields, so we provide standard accessor methods.

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
}
