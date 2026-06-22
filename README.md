# Spring Security CSRF Lab (Modify Data Safely)

A focused Spring Boot project to **experiment with CSRF protection on state-changing requests** (`POST`, `PUT`, `DELETE`).

This repository is intentionally small so you can clearly observe:
- what happens when a CSRF token is missing,
- how to fetch a token,
- how to send it correctly when modifying data.

## Why this project exists

In Spring Security, CSRF protection is enabled by default for browser-style sessions. That means:
- safe requests like `GET` usually work without a CSRF token,
- unsafe requests like `POST` are rejected unless a valid token is present.

This app gives you a minimal API where that behavior is easy to test.

## Tech stack

- Java `25`
- Spring Boot `4.1.0`
- Spring Security (default config)
- Spring MVC
- Lombok
- Maven Wrapper (`./mvnw`)

## Current auth and security setup

No custom `SecurityFilterChain` is defined, so Spring Security defaults apply.

Configured demo credentials (from `application.yaml`):
- Username: `user`
- Password: `passer`

## API surface

| Method | Path | Description | CSRF required |
|---|---|---|---|
| `GET` | `/` | Request/session debug info | No |
| `GET` | `/students` | List all students (in-memory) | No |
| `POST` | `/students` | Add a student (in-memory) | Yes |
| `GET` | `/csrf-token` | Returns current CSRF token details | No (used to fetch token) |

> Data is stored in memory (`ArrayList`) and resets on restart.

---

## Quick start

```bash
./mvnw spring-boot:run
```

App starts on: `http://localhost:8080`

## CSRF experiment flow (copy/paste)

### 1) Read data (works without token)

```bash
curl -u user:passer http://localhost:8080/students
```

### 2) Try modifying data without token (should fail)

```bash
curl -i -u user:passer \
  -X POST http://localhost:8080/students \
  -H "Content-Type: application/json" \
  -d '{"id":4,"firstName":"Alice","lastName":"Brown"}'
```

Expected: `403 Forbidden`

### 3) Fetch token and keep session cookie

```bash
curl -u user:passer -c cookies.txt http://localhost:8080/csrf-token
```

Sample response shape:

```json
{
  "headerName": "X-CSRF-TOKEN",
  "parameterName": "_csrf",
  "token": "..."
}
```

Copy the `token` value.

### 4) Retry POST with token + same session cookie

```bash
curl -i -u user:passer -b cookies.txt \
  -X POST http://localhost:8080/students \
  -H "Content-Type: application/json" \
  -H "X-CSRF-TOKEN: <PASTE_TOKEN_HERE>" \
  -d '{"id":4,"firstName":"Alice","lastName":"Brown"}'
```

Expected: success response with the added student object.

### 5) Verify state changed

```bash
curl -u user:passer http://localhost:8080/students
```

---

## Run tests

```bash
./mvnw test
```

Current tests include a basic context-load check.

## What to try next

1. Add `PUT /students/{id}` and `DELETE /students/{id}` and verify CSRF behavior.
2. Add MockMvc security tests that assert `403` without token and `200` with token.
3. Introduce a custom `SecurityFilterChain` and compare behavior (e.g., disable CSRF for non-browser APIs).
4. Add a tiny HTML form page to observe token handling in browser form posts.

## Project structure

- `src/main/java/sn/sdley/my_spring_boot_app2_security/HomeController.java`
- `src/main/java/sn/sdley/my_spring_boot_app2_security/StudentController.java`
- `src/main/java/sn/sdley/my_spring_boot_app2_security/Student.java`
- `src/main/resources/application.yaml`

---

If your goal is learning, this is a great baseline: small enough to understand quickly, realistic enough to demonstrate how CSRF protects data-changing endpoints.

