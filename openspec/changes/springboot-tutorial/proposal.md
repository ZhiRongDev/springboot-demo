## Why

Learning Spring Boot through code alone is hard — annotations like `@SpringBootApplication`, `@Autowired`, `@Service`, and `@Bean` have implicit magic that's not obvious from reading source files. Detailed markdown tutorials, written alongside the User REST API implementation, give the learner a conceptual map to understand how all the pieces connect.

## What Changes

- Add `docs/01-spring-boot-overview.md` — introduction to Spring Boot, auto-configuration, and the application entry point
- Add `docs/02-dependency-injection.md` — in-depth guide to IoC, DI, and the Spring container
- Add `docs/03-beans-and-annotations.md` — how Spring creates and manages beans using stereotype annotations and `@Bean`
- Add `docs/04-layered-architecture.md` — how Controller → Service → Repository are wired together in this project
- Add `docs/05-validation-and-errors.md` — Bean Validation, `@Valid`, and global exception handling
- Add annotated source files — the actual Java code from the user-rest-api change with extensive inline comments explaining each annotation and design decision

## Capabilities

### New Capabilities

- `tutorial-overview`: High-level Spring Boot concepts, auto-configuration, and entry point explanation
- `tutorial-di`: Deep dive into Inversion of Control, Dependency Injection, and the ApplicationContext
- `tutorial-beans`: Bean lifecycle, stereotype annotations, `@Bean`, `@Configuration`, and `@Component`
- `tutorial-layers`: How the 3-layer architecture works in practice with constructor injection
- `tutorial-validation`: `@Valid`, constraint annotations, BindingResult, and `@RestControllerAdvice`

### Modified Capabilities

<!-- None — all new documentation -->

## Impact

- New `docs/` folder at project root with 5 markdown tutorial files
- Tutorial files reference the actual source files from `user-rest-api` implementation
- No production code changes — documentation only
