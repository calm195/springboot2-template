# Spring Boot 2 Template

English | [简体中文](README.md)

A Java 8 backend starter with a working REST API, input validation, consistent error responses, health checks, formatting, code checks, tests, and Codex CLI project instructions. No frontend or TypeScript dependencies.

> **Version policy:** Spring Boot **2.7.18** is the final publicly available OSS stable 2.x release. OSS maintenance ended in November 2023; it is not the currently maintained Spring Boot generation. This template targets Java 8 / Boot 2 compatibility. Evaluate commercial support or migration to a supported version before production use. [Official release announcement](https://spring.io/blog/2023/11/23/spring-boot-2-7-18-available-now/)

## Requirements and startup

- JDK **8**, with `JAVA_HOME` configured. The build enforces Java 8.
- Maven Wrapper pins **3.9.9**. No Maven installation is required; the first run needs internet access and `curl` / `wget` (PowerShell on Windows).

```sh
# macOS example; use your actual JDK 8 location on other platforms
export JAVA_HOME=$(/usr/libexec/java_home -v 1.8)
./mvnw spring-boot:run
```

Use `mvnw.cmd` on Windows. The default port is `8080`; override it with `SERVER_PORT`.

```sh
curl 'http://localhost:8080/api/v1/greetings?name=Codex'
# {"message":"Hello, Codex!"}
curl http://localhost:8080/actuator/health
# {"status":"UP"}
```

Omitting `name` uses `World`. Whitespace-only names or names longer than 80 characters return HTTP 400:

```json
{"code":"INVALID_REQUEST","message":"Request validation failed"}
```

APIs are versioned under `/api/v1`. Only Actuator `health` / `info` are exposed, and health details are hidden. Authentication is not configured; add identity, authorization and deployment boundaries appropriate to your application.

## Layout

```text
.
├── src/main/java/com/example/template/
│   ├── TemplateApplication.java   # Entry point at the component-scanning root
│   ├── greeting/                 # Feature-oriented controller / service / DTO
│   └── shared/api/               # Shared error contract and exception handling
├── src/main/resources/
│   └── application.yml           # Environment-driven base configuration
├── src/test/java/                # HTTP contracts, validation and health tests
├── config/checkstyle.xml         # Naming, imports, braces and Javadoc rules
├── .mvn/wrapper/                 # Pinned Maven version
├── .github/workflows/ci.yml      # JDK 8 quality gate
├── AGENTS.md                     # Codex CLI repository instructions
└── pom.xml                       # Dependencies, packaging and quality checks
```

Extend the layout by business feature. Controllers handle HTTP and validation, services own business rules, and DTOs define external contracts. Prefer constructor injection and immutable responses. Document public types with Javadoc and explain design decisions in comments. Add entities, repositories, migrations and tests when persistence is actually needed.

## Development and quality checks

```sh
./mvnw spotless:apply                      # Apply formatting
./mvnw --batch-mode --no-transfer-progress clean verify
java -jar target/springboot2-template-0.0.1-SNAPSHOT.jar
```

`verify` runs Spotless format checks, Checkstyle rules, JUnit 5 / MockMvc tests and executable JAR packaging. GitHub Actions runs the same command. Test reports are in `target/surefire-reports/`. Formatting and code-rule violations fail the build.

Java uses Google Java Format. Text files are checked for trailing whitespace and final newlines; editors use `.editorconfig`. To run the build **on JDK 8 itself**, Spotless 2.30.0, Google Java Format 1.7 and Checkstyle 9.3 are pinned. Check runtime requirements before upgrading them. Compiling on Java 8 also prevents accidental use of newer JDK APIs.

Provide configuration through environment variables or external Spring configuration. Never commit passwords, tokens or real environment credentials. Keep both READMEs current when adding configuration. Prefer the Boot BOM for runtime dependency management over individual framework version overrides.

## Codex CLI

The root `AGENTS.md` integrates this repository with Codex CLI through Java 8 constraints, layout conventions, development commands and required checks. Codex reads these instructions when working in this directory. No model name is hardcoded, personal settings are not overwritten, and login credentials are not stored in the repository.

If needed, install using a Node.js environment supported by Codex CLI:

```sh
npm install -g @openai/codex
codex login
codex
```

Start `codex` in the repository root and authenticate with your own account. Running the application or Maven build requires neither Node.js nor an OpenAI API key.

Example task: `Add an input constraint to the greeting feature, follow AGENTS.md, add tests and run ./mvnw clean verify.`

References: [Official Codex CLI documentation](https://developers.openai.com/codex/cli), [AGENTS.md guide](https://developers.openai.com/codex/guides/agents-md).

## Adapting the template

1. Change Maven coordinates and application name; rename the root package and directories.
2. Replace the example `greeting` feature with your APIs and tests.
3. Add persistence, authentication and deployment configuration as needed.
4. Run `./mvnw spotless:apply` and `./mvnw clean verify`, then update both READMEs.
