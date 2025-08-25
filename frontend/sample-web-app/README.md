# Sample Web App (Frontend)

This project is the frontend for the Sample Full-Stack Application. It is a Vue.js application created with Vue CLI that provides a user interface to manage "Items" from the backend API.

## Features

*   CRUD interface for Items (Create, Read, Update, Delete).
*   Displays a sorted list of all items.
*   Form for creating and editing items with basic validation.
*   Handles API errors, including optimistic locking conflicts from the backend.
*   Simple and clean user interface.

## Technologies Used

*   **Vue.js**: 3.x
*   **Vue CLI**: For project scaffolding and development tooling.
*   **Axios**: For making HTTP requests to the backend API.
*   **JavaScript (ES6+)**
*   **HTML5 & CSS3**

## Project Setup

These instructions are for running the frontend in a standalone development mode. For running the complete application with Docker, please see the [root README](../../README.md).

### Prerequisites

*   Node.js (v16.x or later recommended)
*   npm (v8.x or later recommended)

### Install Dependencies

```bash
npm install
```

### Compiles and hot-reloads for development

```bash
npm run serve
```

The application will be available at `http://localhost:8080` (or another port if 8080 is busy).

The development server is configured to proxy API requests from `/api` to a backend server. By default, it proxies to `http://localhost:80` (the Nginx container in the docker-compose setup). If you are running the backend standalone on a different port (e.g., 8080), you may need to create a `vue.config.js` file to adjust the proxy target.

### Compiles and minifies for production

```bash
npm run build
```

The production-ready files will be located in the `dist` directory. These files are served by the Nginx container in the Docker Compose setup.

## Project Structure

*   `public/`: Contains the main `index.html` file and static assets.
*   `src/`: Contains the main Vue application source code.
    *   `App.vue`: The root Vue component.
    *   `components/HelloWorld.vue`: The main component implementing the CRUD functionality for items. It is imported as `ItemCrud` in `App.vue`.
    *   `services/apiService.js`: An Axios-based service for communicating with the backend API.