# QuantityMeasurementApp — Microservices

A Spring Boot microservices application for unit-of-measurement conversion, with JWT + Google OAuth2 authentication.

## Architecture

```
                        ┌─────────────────────┐
                        │     API Gateway      │  :8080
                        │  (Auth + Routing)    │
                        └──────────┬──────────┘
                                   │  Eureka Service Discovery
               ┌───────────────────┼───────────────────┐
               │                   │                   │
       ┌───────▼───────┐   ┌───────▼────────┐  ┌──────▼──────────┐
       │  User Service  │   │Quantity Service│  │ Eureka Server   │
       │    :8081       │   │    :8082       │  │    :8761        │
       └───────┬───────┘   └───────┬────────┘  └─────────────────┘
               └──────────┬────────┘
                    ┌──────▼──────┐
                    │    MySQL    │
                    │   :3306     │
                    └────────────┘
```

## Services

| Service            | Port | Description                          |
|--------------------|------|--------------------------------------|
| `api-gateway`      | 8080 | Entry point, JWT auth, OAuth2, routing |
| `user-service`     | 8081 | User registration & management       |
| `quantity-service` | 8082 | Unit conversion & history            |
| `eureka-service`   | 8761 | Service discovery (Eureka Server)    |
| `mysql`            | 3306 | Persistent database                  |

---

## Quick Start (Docker)

### Prerequisites
- Docker 24+ and Docker Compose v2
- Google OAuth2 credentials ([create here](https://console.cloud.google.com/apis/credentials))

### 1. Configure environment

```bash
make setup       # copies .env.example → .env
# Then edit .env and fill in:
#   GOOGLE_CLIENT_ID, GOOGLE_CLIENT_SECRET
#   MYSQL_ROOT_PASSWORD, MYSQL_PASSWORD
#   JWT_SECRET  (generate with: openssl rand -hex 32)
```

### 2. Build images

```bash
make build
```

### 3. Start all services

```bash
make up
```

Services start in dependency order. The full stack takes ~60–90 seconds to be ready.

### 4. Verify

```bash
make ps            # show container statuses
make logs          # tail all logs
```

- Eureka dashboard: http://localhost:8761
- API Gateway: http://localhost:8080
- Health checks: http://localhost:8080/actuator/health

---

## Development Mode

Development mode enables verbose logging and `show-sql: true`:

```bash
make up-dev
```

---

## Useful Commands

```bash
make logs-api-gateway      # tail only the gateway logs
make logs-user-service     # tail only user-service logs
make restart               # restart all services
make down                  # stop containers (keeps volumes)
make clean                 # stop + remove volumes (wipes DB!)
make nuke                  # remove everything including images
make mvn-build             # rebuild all JARs locally
```

---

## Environment Variables Reference

| Variable              | Required | Default          | Description                        |
|-----------------------|----------|------------------|------------------------------------|
| `MYSQL_ROOT_PASSWORD` | ✅       | —                | MySQL root password                |
| `MYSQL_USER`          | ✅       | —                | App DB username                    |
| `MYSQL_PASSWORD`      | ✅       | —                | App DB password                    |
| `MYSQL_DATABASE`      | ✅       | `quantity_db`    | Database name                      |
| `JWT_SECRET`          | ✅       | —                | Hex-encoded secret (min 32 bytes)  |
| `JWT_EXPIRATION_MS`   | ❌       | `86400000` (1d)  | JWT TTL in milliseconds            |
| `GOOGLE_CLIENT_ID`    | ✅       | —                | Google OAuth2 client ID            |
| `GOOGLE_CLIENT_SECRET`| ✅       | —                | Google OAuth2 client secret        |
| `SPRING_PROFILE`      | ❌       | `prod`           | Spring active profile              |
| `LOG_LEVEL`           | ❌       | `INFO`           | Logging level                      |
| `JPA_DDL_AUTO`        | ❌       | `update`         | Hibernate DDL strategy             |

---

## Production Checklist

- [ ] Replace `JPA_DDL_AUTO=update` with `validate` after initial deploy
- [ ] Set `LOG_LEVEL=WARN` or `ERROR` in production
- [ ] Store secrets in a vault (AWS Secrets Manager, HashiCorp Vault, etc.)
- [ ] Enable HTTPS via a reverse proxy (nginx/traefik) in front of the gateway
- [ ] Set up database backups for the `mysql-data` volume
- [ ] Pin Docker image tags to specific digests for reproducibility
- [ ] Configure rate limiting in the API Gateway

---

## Project Structure

```
QuantityMeasurementApp/
├── .env.example              ← safe template (commit this)
├── .env                      ← your real secrets (NEVER commit)
├── .dockerignore
├── docker-compose.yml        ← production stack
├── docker-compose.dev.yml    ← development overrides
├── Makefile                  ← helper commands
├── pom.xml                   ← parent POM
├── docker/
│   └── mysql/
│       └── init.sql          ← DB init script
├── eureka-service/
│   ├── Dockerfile
│   └── src/main/resources/application.yaml
├── api-gateway/
│   ├── Dockerfile
│   └── src/main/resources/application.yaml
├── user-service/
│   ├── Dockerfile
│   └── src/main/resources/application.yaml
└── quantity-service/
    ├── Dockerfile
    └── src/main/resources/application.yaml
```
