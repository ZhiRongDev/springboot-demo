package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * DemoApplication is the entry point for the entire Spring Boot application.
 *
 * @SpringBootApplication is a convenience annotation that combines THREE annotations:
 *
 *   1. @Configuration
 *      Marks this class as a source of bean definitions (methods annotated with @Bean).
 *      The passwordEncoder() method below is only processed because of this.
 *
 *   2. @EnableAutoConfiguration
 *      Tells Spring Boot to automatically configure beans based on what's on the
 *      classpath. For example:
 *        - spring-boot-starter-webmvc  → auto-configures DispatcherServlet, Jackson, etc.
 *        - spring-boot-starter-validation → auto-configures the validator
 *      You don't have to wire these up manually.
 *
 *   3. @ComponentScan
 *      Scans this package (com.example.demo) and all sub-packages for classes
 *      annotated with @Component, @Service, @Repository, @RestController, etc.
 *      and registers them as beans in the ApplicationContext.
 *
 * SpringApplication.run() bootstraps the entire application:
 *   - Creates the ApplicationContext (the Spring IoC container)
 *   - Runs @ComponentScan to discover all beans
 *   - Wires dependencies (dependency injection)
 *   - Starts the embedded Tomcat server
 */
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	/**
	 * Declares PasswordEncoder as a Spring bean.
	 *
	 * @Bean — marks this method as a bean factory method. Spring calls this method
	 * once at startup and stores the returned object in the ApplicationContext.
	 * Any class that declares a PasswordEncoder constructor parameter (like UserService)
	 * will automatically receive this instance.
	 *
	 * Why declare it here instead of just doing `new BCryptPasswordEncoder()` inside UserService?
	 *   - The bean is shared: only one instance is created (singleton by default).
	 *   - It's testable: in tests you can provide a mock PasswordEncoder bean instead.
	 *   - It's the Spring way: third-party objects you don't own (BCryptPasswordEncoder
	 *     is from spring-security-crypto) are declared as @Bean rather than annotated
	 *     with @Component.
	 *
	 * BCryptPasswordEncoder:
	 *   - Uses the BCrypt hashing function with a random salt (strength 10 by default).
	 *   - encode("secret") → "$2a$10$..." (different hash each time due to random salt)
	 *   - matches("secret", hash) → true (verifies without knowing the salt — it's embedded)
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

