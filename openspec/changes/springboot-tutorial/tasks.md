## 1. Setup

- [ ] 1.1 Create `docs/` directory at project root
- [ ] 1.2 Verify that the `user-rest-api` change is fully implemented before writing tutorials (tutorials reference specific source files)

## 2. Tutorial 1 — Spring Boot Overview (`docs/01-spring-boot-overview.md`)

- [ ] 2.1 Write TL;DR section (3-5 bullet summary)
- [ ] 2.2 Write "What is Spring Boot?" section explaining how it sits on top of Spring Framework and removes boilerplate configuration
- [ ] 2.3 Write "The Entry Point" section with annotated `DemoApplication.java` snippet explaining `@SpringBootApplication` = `@Configuration` + `@EnableAutoConfiguration` + `@ComponentScan`
- [ ] 2.4 Write "Auto-Configuration" section explaining classpath detection (e.g., `spring-boot-starter-webmvc` → auto-registers `DispatcherServlet`)
- [ ] 2.5 Write "What Happens at Startup" section with a numbered sequence: scan → detect → configure → start

## 3. Tutorial 2 — Dependency Injection (`docs/02-dependency-injection.md`)

- [ ] 3.1 Write TL;DR section
- [ ] 3.2 Write "The Problem: Tight Coupling" section showing a plain Java example where classes `new` their own dependencies
- [ ] 3.3 Write "Inversion of Control" section explaining the IoC principle with the Hollywood Principle analogy
- [ ] 3.4 Write "The Spring Container (ApplicationContext)" section explaining it as a registry that creates and wires objects
- [ ] 3.5 Write "Field Injection vs Constructor Injection" section with side-by-side examples and reasons to prefer constructor injection
- [ ] 3.6 Write "How Spring Resolves Dependencies" section explaining the startup sequence: scan → instantiate → inject

## 4. Tutorial 3 — Beans and Annotations (`docs/03-beans-and-annotations.md`)

- [ ] 4.1 Write TL;DR section
- [ ] 4.2 Write "What is a Bean?" section defining a Spring-managed object and its singleton default scope
- [ ] 4.3 Write "Stereotype Annotations" section with a table: `@Component` (generic), `@Service` (business logic), `@Repository` (data access + exception translation), `@RestController` (HTTP + `@ResponseBody`)
- [ ] 4.4 Write "Declaring Third-Party Beans with @Bean" section using `PasswordEncoder` as the concrete example, showing `@Configuration` class or method in `DemoApplication`
- [ ] 4.5 Write "Bean Scope Quick Reference" section covering singleton, prototype, and mentioning `@PostConstruct`
- [ ] 4.6 Include an ASCII diagram showing `@ComponentScan` discovering stereotype-annotated classes

## 5. Tutorial 4 — Layered Architecture (`docs/04-layered-architecture.md`)

- [ ] 5.1 Write TL;DR section
- [ ] 5.2 Write "The Three Layers" section with an ASCII box diagram: HTTP Request → Controller → Service → Repository → Storage
- [ ] 5.3 Write "The Controller Layer" section with annotated `UserController` snippet covering `@RestController`, `@RequestMapping`, `@PostMapping`, `@RequestBody`, `@PathVariable`, `ResponseEntity`
- [ ] 5.4 Write "The Service Layer" section with annotated `UserService` snippet covering `@Service`, constructor injection, and business logic responsibilities
- [ ] 5.5 Write "The Repository Layer" section with annotated `UserRepository` snippet covering `@Repository`, `ConcurrentHashMap`, and `AtomicLong`
- [ ] 5.6 Write "Request Lifecycle Walkthrough" section tracing `POST /api/auth/register` step by step through all three layers

## 6. Tutorial 5 — Validation and Error Handling (`docs/05-validation-and-errors.md`)

- [ ] 6.1 Write TL;DR section
- [ ] 6.2 Write "Bean Validation (JSR-380)" section listing common annotations with examples from `RegisterRequest.java`: `@NotBlank`, `@Email`, `@Size`, `@NotNull`
- [ ] 6.3 Write "Triggering Validation with @Valid" section showing the controller method signature and explaining the automatic `MethodArgumentNotValidException` on failure
- [ ] 6.4 Write "Global Exception Handling with @RestControllerAdvice" section with fully annotated `GlobalExceptionHandler.java` snippet
- [ ] 6.5 Write "Error Response Shape" section showing the JSON error structure and the importance of consistency
- [ ] 6.6 Write "Custom Exceptions" section showing `UserNotFoundException` and `EmailAlreadyExistsException` and how they're thrown from the service and caught by the handler

## 7. Review

- [ ] 7.1 Read each tutorial file top-to-bottom and verify all code snippets match the actual implementation
- [ ] 7.2 Ensure each tutorial starts with a TL;DR section
- [ ] 7.3 Verify all cross-references between tutorials (e.g., "as explained in doc 02") are accurate
