# Astrapay Simple Notes - Spring Boot

## Overview
A simple notes REST API built with Spring Boot, following Astrapay's backend conventions. This project allows you to create, list, and delete notes, each with a specific type.

**Note:** This project does not use any database. All notes are stored in memory only and will be lost when the application stops.

When the application starts for the first time, it will automatically create 6 initial notes. This initialization is handled in the `NoteInitializer` class.

## How to Run
1. Ensure you have Java 11+ and Maven installed.
2. Open a terminal in the project root directory.
3. Start the application with:
   ```
   mvn spring-boot:run
   ```
4. The application will run on port 8080 by default.

## Features
- **Create a note**: `POST /notes` (with content and type)
- **List all notes**: `GET /notes`
- **Delete a note**: `DELETE /notes/{id}`
- **Note types**: PERSONAL, WORK, REMINDER, OTHER

## API Documentation (Swagger)
- After starting the application, open your browser and go to:
  [http://localhost:8080/swagger-ui/](http://localhost:8080/swagger-ui/)
  to view and test the API documentation interactively.
