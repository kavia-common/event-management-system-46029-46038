# Event Management System - Backend

This Spring Boot application provides RESTful APIs to manage events and attendances.

Quick start:
- Java 17
- Gradle wrapper included

Run:
```
cd event-management-system-46029-46038/event_management_backend
./gradlew bootRun
```

OpenAPI/Swagger:
- OpenAPI JSON: /v3/api-docs
- Swagger UI: /swagger-ui.html
- Convenience redirect: /docs

Health:
- /health (simple)
- /actuator/health (actuator)

Core Endpoints:

1) Events
- Create
```
curl -X POST http://localhost:8080/api/events \
  -H "Content-Type: application/json" \
  -d '{
    "title":"Tech Conference 2025",
    "description":"A conference about modern technologies.",
    "startTime":"2025-12-01T09:00:00Z",
    "endTime":"2025-12-01T17:00:00Z",
    "location":"San Francisco, CA",
    "capacity":200,
    "organizer":"ACME Events",
    "tags":["tech","conference"]
  }'
```

- List (with filters and pagination)
```
curl "http://localhost:8080/api/events?from=2025-12-01T00:00:00Z&to=2025-12-31T23:59:59Z&organizer=ACME&tag=tech&page=0&size=10"
```

- Get by id
```
curl http://localhost:8080/api/events/1
```

- Update
```
curl -X PUT http://localhost:8080/api/events/1 \
  -H "Content-Type: application/json" \
  -d '{
    "title":"Tech Conference 2025 - Updated",
    "description":"Updated desc",
    "startTime":"2025-12-01T09:00:00Z",
    "endTime":"2025-12-01T18:00:00Z",
    "location":"San Francisco, CA",
    "capacity":250,
    "organizer":"ACME Events",
    "tags":["tech","conference","cloud"]
  }'
```

- Delete
```
curl -X DELETE http://localhost:8080/api/events/1
```

2) Attendees
- Register (enforces capacity and prevents duplicates)
```
curl -X POST http://localhost:8080/api/events/1/attendees \
  -H "Content-Type: application/json" \
  -d '{"name":"Jane Doe","email":"jane@example.com"}'
```

- Unregister
```
curl -X DELETE http://localhost:8080/api/events/1/attendees/2
```

- List attendees
```
curl http://localhost:8080/api/events/1/attendees
```

- Count
```
curl http://localhost:8080/api/events/1/attendees/count
```

Notes:
- Uses in-memory H2 with seed data (see DataInitializer).
- CORS is permissive for development.
- Returns structured error responses with proper HTTP status codes.