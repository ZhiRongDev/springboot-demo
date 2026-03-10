## ADDED Requirements

### Requirement: Beans and annotations tutorial
The project SHALL include `docs/03-beans-and-annotations.md` that explains what a Spring bean is, how stereotype annotations create beans, and how `@Bean` and `@Configuration` work.

#### Scenario: Bean definition explanation
- **WHEN** a learner reads the tutorial
- **THEN** it SHALL explain that a bean is any object managed by the Spring container, created once (by default singleton scope) and injected wherever needed

#### Scenario: Stereotype annotations
- **WHEN** a learner reads the tutorial
- **THEN** it SHALL explain `@Component`, `@Service`, `@Repository`, and `@RestController` as specializations of `@Component`, with a table showing each annotation, its intended layer, and any extra behavior it adds

#### Scenario: @Bean and @Configuration
- **WHEN** a learner reads the tutorial
- **THEN** it SHALL show how `PasswordEncoder` is declared as a `@Bean` in `DemoApplication` (or a `@Configuration` class) and explain why this pattern is used for third-party objects you don't own

#### Scenario: Bean lifecycle overview
- **WHEN** a learner reads the tutorial
- **THEN** it SHALL include a brief note on singleton vs prototype scope and the `@PostConstruct` hook as "things to know exist" without deep diving

#### Scenario: TL;DR summary
- **WHEN** a learner opens the file
- **THEN** the first section SHALL be a concise TL;DR (3-5 bullet points) summarizing the key takeaways
