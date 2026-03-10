## ADDED Requirements

### Requirement: Spring Boot overview tutorial
The project SHALL include `docs/01-spring-boot-overview.md` that explains what Spring Boot is, how it differs from plain Spring Framework, and how the application entry point works.

#### Scenario: Entry point explanation
- **WHEN** a learner reads the tutorial
- **THEN** it SHALL explain what `@SpringBootApplication` does (combining `@Configuration`, `@EnableAutoConfiguration`, `@ComponentScan`) with an annotated code snippet from `DemoApplication.java`

#### Scenario: Auto-configuration explanation
- **WHEN** a learner reads the tutorial
- **THEN** it SHALL explain that Spring Boot scans the classpath and automatically configures beans (e.g., a `DispatcherServlet` for web) without manual XML or Java config

#### Scenario: TL;DR summary
- **WHEN** a learner opens the file
- **THEN** the first section SHALL be a concise TL;DR (3-5 bullet points) summarizing the key takeaways
