# Spring Security JWT Demo

A small Spring Boot app showing **JWT-based authentication and token validation** with Spring Security.

## What it does

- Users can register with `POST /register`
- Users can authenticate with `POST /authenticate`
- The app returns a signed JWT on successful login
- Protected endpoints accept `Authorization: Bearer <token>`
- The JWT is validated on each request before access is granted

## Tech stack

- Java `25`
- Spring Boot `4.1.0`
- Spring Security
- Spring MVC
- Spring Data JPA
- PostgreSQL
- JWT (`jjwt`)
- Maven Wrapper (`./mvnw`)

## Authentication flow

1. Register a user:

```bash
curl -X POST http://localhost:8080/register \
  -H "Content-Type: application/json" \
  -d '{"username":"alice","password":"secret"}'
```

2. Authenticate and receive a token:

```bash
curl -X POST http://localhost:8080/authenticate \
  -H "Content-Type: application/json" \
  -d '{"username":"alice","password":"secret"}'
```

3. Use the token to access protected resources:

```bash
curl http://localhost:8080/students \
  -H "Authorization: Bearer <PASTE_TOKEN_HERE>"
```

## Default security behavior

- `/register`, `/login`, `/authenticate`, and `/error` are public
- All other routes require authentication
- Basic form login is still available at `/login`
- JWT validation runs before protected requests are processed

## Demo data

The app seeds example users at startup:

- Username: `sdley`
- Password: `passer`
- Username: `user`
- Password: `password`

## Run the app

```bash
docker compose up -d
./mvnw spring-boot:run
```

App URL: `http://localhost:8080`

## Test

```bash
./mvnw test
```

## Configuration

JWT settings are configurable through environment variables:

- `APP_JWT_SECRET`
- `APP_JWT_EXPIRATION_MS`

If unset, local defaults are used.
