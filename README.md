# Keycloak.Events

Keycloak Event Listener plugin publish su kien `REGISTER` sang Kafka topic ingest cua `Identity.API`.

## Package layout

Plugin nay dung cach tach lop "clean-lite", khong full Clean Architecture nhu `.NET` services:

- `provider`: adapter cua Keycloak
- `configuration`: doc va validate settings tu environment
- `application`: xu ly use case dang ky
- `domain`: message model
- `ports`: abstraction publish event
- `infrastructure`: Kafka implementation

Muc tieu la giu plugin mong, de test, de mo rong, nhung khong over-engineer thanh nhieu module Maven.

## Build

CI runs on GitHub Actions with JDK 17:

- `.github/workflows/ci.yml`
- `mvn -B clean verify`
- uploads `target/keycloak-events.jar` as a workflow artifact

Recommended on Windows:

```powershell
.\scripts\build.cmd
```

This script uses a portable JDK under `.tools/jdk-17` and Maven Wrapper, so Maven does not need to be installed globally.
Use `.\scripts\build.cmd -Clean` only when you need a clean rebuild.

```bash
./mvnw clean package
```

On Windows:

```powershell
.\mvnw.cmd clean package
```

Artifact output:

- `Keycloak.Events/target/keycloak-events.jar`

## Deploy local

1. Build jar.
2. Copy jar vao `IaC/keycloak/providers/`.
3. Restart Keycloak.
4. Trong Keycloak Admin Console:
   - `Realm Settings`
   - `Events`
   - them listener `event-listener`

Run E2E checklist:

- `IaC/KEYCLOAK_IDENTITY_WALLET_E2E.md`

## Required env vars

- `KEYCLOAK_EVENTS_KAFKA_BOOTSTRAP_SERVERS`
- `KEYCLOAK_EVENTS_KAFKA_TOPIC`

## Optional env vars

- `KEYCLOAK_EVENTS_KAFKA_CLIENT_ID`
- `KEYCLOAK_EVENTS_KAFKA_SECURITY_PROTOCOL`
- `KEYCLOAK_EVENTS_KAFKA_SASL_MECHANISM`
- `KEYCLOAK_EVENTS_KAFKA_SASL_USERNAME`
- `KEYCLOAK_EVENTS_KAFKA_SASL_PASSWORD`
