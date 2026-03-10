## Context

The learner is new to Spring Boot and has just scaffolded a project. They have a working `user-rest-api` implementation to read alongside the tutorials. The tutorials must be self-contained enough to read without running the app, but also reference specific files from the implementation to ground abstract concepts in real code.

## Goals / Non-Goals

**Goals:**
- Explain *why* Spring Boot does things, not just *how*
- Map every key annotation to a concrete example in the project
- Show the "wiring diagram" — how Spring connects the beans at startup
- Cover: `@SpringBootApplication`, `@RestController`, `@Service`, `@Repository`, `@Bean`, `@Autowired`, `@Component`, constructor injection, `@Valid`, `@ControllerAdvice`
- Use code snippets with inline comments explaining each line/annotation
- Teach the IoC (Inversion of Control) mental model clearly

**Non-Goals:**
- Spring Security, JPA, testing, reactive programming, Spring Cloud
- Framework internals (proxy generation, reflection internals)
- Production deployment, Docker, or CI/CD

## Decisions

### 1. Tutorial files go in `docs/` at project root

**Decision**: Create `docs/01-spring-boot-overview.md` through `docs/05-validation-and-errors.md`.

**Why**: Keeps documentation separate from source code but co-located with the project. Numbered prefix enforces reading order.

---

### 2. Code snippets are annotated copies, not references

**Decision**: Each tutorial includes code blocks with detailed `//` comments explaining each annotation and design choice inline.

**Why**: Learners shouldn't need to jump between files to understand a concept. The annotated snippet is the explanation.

---

### 3. Progressive disclosure — each doc builds on the last

**Decision**: Order topics from macro to micro:
1. What is Spring Boot? (the big picture)
2. IoC and Dependency Injection (the core mechanism)
3. Beans and annotations (how to define managed objects)
4. Layered architecture in practice (applying DI to our code)
5. Validation and error handling (the remaining cross-cutting concern)

**Why**: Readers who stop after doc 2 still understand the most important concept. Each doc references the previous one.

---

### 4. Diagrams as ASCII art

**Decision**: Use ASCII box diagrams to show the bean wiring and request flow.

**Why**: Markdown renders everywhere. No image dependencies.

## Risks / Trade-offs

- **Tutorials may drift from implementation** → Tasks include a step to write the tutorial AFTER the code is implemented, so the snippets are accurate.
- **Too much detail → overwhelming** → Each tutorial has a "TL;DR" summary at the top; detailed sections follow.
