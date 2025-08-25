# Sample Full-Stack API Service

This project is a full-stack application demonstrating a RESTful API service for managing items, built with Spring Boot, and a web-based client interface built with Vue.js.

The application is containerized using Docker and can be easily run with Docker Compose.

## Architecture

The application consists of two main parts:

*   **Backend**: A Spring Boot application providing a REST API for CRUD operations on "Items". It uses PostgreSQL for data persistence and Redis for caching.
*   **Frontend**: A Vue.js single-page application that consumes the backend API to provide a user interface for managing items.

In the Docker-based setup, an Nginx reverse proxy is used to serve the frontend application and forward API requests to the backend service, all on a single port.

## Technologies

### Backend
*   Java 21
*   Spring Boot 3.3.1
*   Spring Data JPA & Redis
*   PostgreSQL
*   Maven

### Frontend
*   Vue.js 3
*   Vue CLI
*   Axios

### Infrastructure
*   Docker & Docker Compose
*   Nginx (as a reverse proxy)

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

*   Docker and Docker Compose

### Running the Full Application (Recommended)

The easiest way to run the entire application stack (Frontend, Backend, Database, Cache, Proxy) is with Docker Compose. From the root directory of the project, run:

```bash
docker-compose up --build
```

The application will be accessible at `http://localhost:80`.

### Development

For separate development of the frontend and backend, please refer to the specific README files:

*   Backend README
*   Frontend README