# Project instructions for Codex CLI

## Scope and compatibility

- This is a Java 8, Spring Boot 2.7.18 backend template. Do not add a frontend or TypeScript unless requested.
- Use Java 8 syntax and APIs only; use `javax.validation`, not `jakarta.validation`.
- Spring Boot 2.7.18 is the last public OSS 2.x release and has ended OSS support. Do not describe it as a currently maintained framework.
- Keep dependencies managed by the Spring Boot BOM where possible. Verify Java 8 runtime compatibility before changing build plugins.

## Structure and conventions

- Keep the application entry point in `com.example.template` for component scanning.
- Organize business code by feature, for example `greeting/`. Keep genuinely shared HTTP infrastructure in `shared/api/`.
- Keep controllers thin, validate at HTTP boundaries, and use constructor injection.
- Use explicit DTOs; do not return persistence entities. Add persistence abstractions only when a real feature needs them.
- Write Javadoc for public types and explain non-obvious decisions. Avoid comments that merely repeat the code.
- Keep credentials outside version control. Do not broaden actuator exposure or log request secrets.
- Preserve HTTP status semantics when adding exception handlers. Avoid leaking stack traces to clients.

## Commands

Set `JAVA_HOME` to a JDK 8 installation before running Maven. The Enforcer rule requires it.

```sh
./mvnw spotless:apply
./mvnw --batch-mode --no-transfer-progress clean verify
./mvnw spring-boot:run
```

`verify` checks formatting and Checkstyle, runs tests and creates an executable JAR.
Use `mvnw.cmd` instead on Windows.

## Change workflow

- Read related code before editing. Keep changes scoped to the requested task.
- Add meaningful tests for changed behavior, especially validation, status codes and error contracts.
- Run the quality checks above; report any check that could not run.
- Update both `README.md` and `README.en.md` when usage or architecture changes.
- Do not edit generated files in `target/`, commit credentials, or change user-level Codex configuration.
