# Sample API Service

This project is the backend for the Sample Full-Stack Application. It's a Spring Boot application demonstrating a RESTful API for managing items. It includes features such as data persistence with PostgreSQL, caching with Redis, optimistic locking, and API documentation with SpringDoc OpenAPI.

## Technologies Used

*   **Java**: 21 / Jakarta EE
*   **Spring Boot**: 3.3.1
*   **Maven**: Dependency management and build automation
*   **Spring Data JPA**: For database interaction
*   **PostgreSQL**: Primary database
*   **H2 Database**: In-memory database for testing
*   **Spring Data Redis**: For caching
*   **Lombok**: To reduce boilerplate code
*   **SpringDoc OpenAPI**: For API documentation (Swagger UI)
*   **Docker/Docker Compose**: For containerization (optional, but `Dockerfile` and `docker-compose.yml` are present)

## Features

*   **CRUD Operations for Items**:
    *   Create new items
    *   Retrieve items by ID or all items
    *   Update existing items
    *   Delete items
*   **Caching**: Utilizes Redis to cache item data for improved performance.
*   **API Documentation**: Automatically generated API documentation available via Swagger UI.
*   **Validation**: Request payload validation.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

*   Java Development Kit (JDK) 21
*   Maven 3.x
*   Docker and Docker Compose (optional, for running PostgreSQL and Redis in containers)

### Building the Project

To build the project, navigate to the root directory of the project and run:

```bash
mvn clean install
```

### Running the Project

#### 1. Using Docker Compose (Recommended for local development)

Ensure Docker and Docker Compose are installed. This will start PostgreSQL and Redis containers, and then the Spring Boot application.

```bash
docker-compose up --build
```

The application will be accessible at `http://localhost:80`.

#### 2. Running Locally (Requires local PostgreSQL and Redis instances)

If you have PostgreSQL and Redis running locally and configured as per `src/main/resources/application.properties`, you can run the application directly:

```bash
mvn spring-boot:run
```

The application will be accessible at `http://localhost:80`.

## API Endpoints

The API documentation is available via Swagger UI once the application is running:

*   **Swagger UI**: `http://localhost:80/swagger-ui.html`

Common endpoints for `Item` resource:

*   `GET /api/items`: Get all items
*   `GET /api/items/{id}`: Get item by ID
*   `POST /api/items`: Create a new item
*   `PUT /api/items/{id}`: Update an existing item
*   `DELETE /api/items/{id}`: Delete an item

## Testing

To run the unit and integration tests, execute the following command in the project root directory:

```bash
mvn test
```
