# Backend setup

The backend is a Java 17 / Spring Boot 3 application. Keep this folder separate from `../frontend` and `../database`.

## Local run

1. Create/select a MySQL database named `student_assignment_db`, then run `../database/schema.sql` against it. The schema file does not create/select a database so it can also run with managed-cloud database users.
2. Set the required environment variables in your shell. Use your own MySQL account and generate a private JWT secret of at least 32 bytes; do not commit these values.
   - `DB_URL` (default: `jdbc:mysql://localhost:3306/student_assignment_db?serverTimezone=UTC`)
   - `DB_USERNAME`, `DB_PASSWORD`
   - `JWT_SECRET` (required; at least 32 bytes)
   - Optional: `PORT` (default `8080`), `CORS_ALLOWED_ORIGINS` (comma-separated exact origins), `UPLOAD_DIR` (default `./uploads`)
3. From this folder run `mvn spring-boot:run`.

`JPA_DDL_AUTO` defaults to `validate`; the schema must already exist. Do not use `update` for production data. The API is under `/api`. Passwords are BCrypt-hashed and protected routes use `Authorization: Bearer <token>`.

## Separate deployment

- Provision a MySQL-compatible managed database and run the SQL schema against the provider-created database. Supply the JDBC URL and credentials as `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD`.
- Build with `mvn package`; deploy `target/student-assignment-management-1.0.0.jar` to a Java 17 runtime. Supply `JWT_SECRET`, `CORS_ALLOWED_ORIGINS`, and the database variables in the service's secret/configuration settings.
- Uploaded files are stored on local disk in `UPLOAD_DIR`. A cloud runtime needs a persistent mounted volume at that path, or a future object-storage adapter; an ephemeral container disk will lose uploads when replaced.
- Host the separate static frontend from `../frontend`. Set `frontend/public/config.js` `apiBaseUrl` to the deployed backend's HTTPS API base, ending in `/api`, and add the frontend origin to `CORS_ALLOWED_ORIGINS`.
- The included SQL seed rows provide example accounts and assignments. Remove or replace those sample inserts before loading real production data; faculty self-registration is enabled to match the existing registration form, and the project has no approval/admin workflow to infer another policy.

## API routes used by the frontend

- `POST /api/auth/register`, `POST /api/auth/login`
- `GET /api/assignments`, `GET /api/assignments/{id}`, `GET /api/assignments/faculty`
- `POST /api/assignments/create`, `PUT /api/assignments/{id}`, `DELETE /api/assignments/{id}`, `GET /api/assignments/{id}/download`
- `GET /api/submissions/dashboard`, `GET /api/submissions/my`, `GET /api/submissions/assignment/{id}`
- `POST /api/submissions/upload`, `PUT /api/submissions/grade/{id}`, `GET /api/submissions/{id}/download`

Data routes return `{ "data": ... }`; authentication returns the token and user fields at the top level, matching the existing JavaScript client.
