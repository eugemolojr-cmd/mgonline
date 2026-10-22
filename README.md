# Metroguards - Payslip Platform

Three independent codebases behind an nginx gateway, sharing PostgreSQL and Keycloak.

| Folder             | Role                                                    | Port (internal) |
|--------------------|---------------------------------------------------------|-----------------|
| `frontend/`        | React (Vite) SPA, served by nginx, logs in via Keycloak | 80              |
| `backend-service/` | Spring Boot REST API: employees + payslip records       | 8080 (x2)       |
| `reports-service/` | Spring Boot: payslip retrieval (JSON) + PDF generation  | 8081 (x2)       |
| `gateway/`         | nginx load balancer / single entry point                | 80 (published)  |
| `keycloak/`        | Realm auto-imported on first start                      | 8080            |

## Run

    docker compose up --build

Open http://localhost  -> Keycloak login.

| User        | Password    | Roles                |
|-------------|-------------|----------------------|
| `employee1` | `employee123` | EMPLOYEE           |
| `hr.admin`  | `hradmin123`  | HR_ADMIN, EMPLOYEE |

Keycloak admin console: http://localhost/auth  (credentials in `.env`).
First start takes about a minute (Keycloak + Maven builds).

## Request flow

    Browser -> nginx :80
                 /            -> frontend
                 /auth/       -> keycloak
                 /api/reports -> reports-service (round-robin) -> backend-service (forwards the user's JWT)
                 /api/        -> backend-service (round-robin)  -> PostgreSQL

## Payslip REST API

Get a token (dev only; the `metroguards-web` client has direct grants enabled for testing):

    TOKEN=$(curl -s -d client_id=metroguards-web -d username=employee1 -d password=employee123 \
      -d grant_type=password http://localhost/auth/realms/metroguards/protocol/openid-connect/token | jq -r .access_token)

    curl -H "Authorization: Bearer $TOKEN" http://localhost/api/reports/payslips/me
    curl -H "Authorization: Bearer $TOKEN" http://localhost/api/reports/payslips/1
    curl -H "Authorization: Bearer $TOKEN" -o payslip.pdf http://localhost/api/reports/payslips/1/pdf

| Service  | Endpoint                               | Access                      |
|----------|----------------------------------------|-----------------------------|
| reports  | GET `/api/reports/payslips/me`         | any logged-in user          |
| reports  | GET `/api/reports/payslips/{id}`       | owner or HR_ADMIN           |
| reports  | GET `/api/reports/payslips/{id}/pdf`   | owner or HR_ADMIN           |
| backend  | GET `/api/payslips/me`                 | EMPLOYEE / HR_ADMIN         |
| backend  | GET `/api/payslips?employeeId=`        | HR_ADMIN                    |
| backend  | POST `/api/payslips`                   | HR_ADMIN                    |
| backend  | GET/POST/PUT/DELETE `/api/employees`   | HR_ADMIN                    |

## Code layout (package-by-feature, layered inside)

    com.metroguards.backend
      config/            SecurityConfig (JWT + Keycloak role mapping)
      common/            exception handler, auth helpers
      employee/          controller / model / repository / service / dto
      payslip/           controller / model / repository / service / dto

    com.metroguards.reports
      client/            BackendClient (forwards the JWT to backend-service)
      payslip/           controller / model / service (report + pdf)

Adding a new function (e.g. `attendance`) = a new sibling package with the same
controller / model / repository / service / dto folders.

## Before production

- Replace `start-dev` with `start` in `docker-compose.yml`, use HTTPS and a real hostname
  (`KC_HOSTNAME`, issuer URLs, redirect URIs).
- Disable `directAccessGrantsEnabled` on the `metroguards-web` client.
- Remove `V2__seed_dev_data.sql` and the demo Keycloak users; change all passwords in `.env`.
- Give Keycloak and the app separate DB users.
- After changing replica counts, restart the gateway (`docker compose restart gateway`) so nginx re-resolves the replicas.
