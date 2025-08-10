# Sample REST API Service

This project is a sample REST API service built with Spring Boot. It provides a complete, layered application architecture for managing "Items," demonstrating best practices for building modern web services.

## Features

- **RESTful API**: Full CRUD (Create, Read, Update, Delete) operations for managing items.
- **Spring Boot 3**: Utilizes the latest features for rapid, convention-over-configuration development.
- **PostgreSQL Database**: Uses Spring Data JPA for robust, persistent storage.
- **Redis Cache**: Implements a caching layer with Spring Cache and Redis to improve performance and reduce database load.
- **API Documentation**: Integrated Swagger UI (via springdoc-openapi) for interactive API documentation and testing.
- **Containerized**: Fully containerized with Docker and Docker Compose for easy, consistent setup and deployment.
- **Input Validation**: Ensures data integrity for incoming requests.
- **CORS Configuration**: Global CORS policy to allow cross-origin requests from frontend applications.

## Prerequisites

Before you begin, ensure you have the following installed on your system:
- [Docker](https://www.docker.com/get-started)
- [Docker Compose](https://docs.docker.com/compose/install/) (typically included with Docker Desktop)

## How to Run the Application

You can run the entire application stack (Spring Boot App, PostgreSQL, Redis) with a single command.

1.  **Clone the repository** (if you haven't already).

2.  **Start the services using Docker Compose**:
    From the root directory of the project, run the following command:
    ```sh
    docker-compose up --build
    ```
    This command will:
    - Build the Docker image for the Spring Boot application.
    - Start containers for the application, a PostgreSQL database, and a Redis instance.
    - Link the services on a shared network.

The API will be running and accessible on port 80 of your local machine.

## Accessing the API

### API Endpoints

The API is available at `http://localhost/api/items`. You can use tools like `curl` or Postman to interact with it.

- **Create an Item:**
  ```sh
  curl -X POST http://localhost/api/items \
  -H "Content-Type: application/json" \
  -d '{"name": "My First Item", "description": "This is a test item."}'
  ```

- **Get All Items:**
  ```sh
  curl http://localhost/api/items
  ```

- **Delete an Item (e.g., with ID 1):**
  ```sh
  curl -X DELETE http://localhost/api/items/1
  ```

### Swagger UI API Documentation

For interactive API documentation, open your web browser and navigate to:
**http://localhost/swagger-ui.html**

Here you can view all available endpoints, see their request/response models, and execute API calls directly from the browser.