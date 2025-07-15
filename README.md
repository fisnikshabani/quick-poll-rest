# Quick Poll Application

A RESTful API for creating and managing polls built with Spring Boot. This application demonstrates best practices for building versioned, secure REST APIs.

## Overview

Quick Poll is a comprehensive polling application that allows users to:
- Create polls with multiple options (2-6 options per poll)
- Vote on existing polls
- View poll results and statistics
- Manage polls with authentication and authorization

## Features

- **API Versioning**: Three API versions (v1, v2, v3) with progressive enhancements
- **HATEOAS Support**: v1 API includes hypermedia links for better discoverability
- **Pagination**: v2 and v3 APIs support pagination for better performance
- **Security**: Role-based access control with HTTP Basic Authentication
- **Validation**: Input validation for all API endpoints
- **Exception Handling**: Comprehensive error handling with meaningful error messages
- **API Documentation**: OpenAPI/Swagger documentation for all endpoints

## Technologies Used

- Java 21
- Spring Boot 3.2.4
- Spring Data JPA
- Spring Security
- Spring HATEOAS
- HSQLDB (embedded database)
- SpringDoc OpenAPI 2.5.0
- Maven

## Project Structure

The application follows a multi-layered architecture:
- `domain`: Entity classes (Poll, Option, Vote, User)
- `repository`: Spring Data JPA repositories
- `controller`: REST controllers organized by API version
- `exception`: Custom exceptions and error handling
- `dto`: Data Transfer Objects for API responses
- `security`: Security configuration and user details service
- `config`: Application configuration classes

## API Versions

### v1 (/v1)
- Basic CRUD operations
- HATEOAS links for resource navigation
- Public access (no authentication required)

### v2 (/v2)
- All v1 features
- Pagination support for listing polls
- Public access (no authentication required)

### v3 (/v3)
- All v2 features
- Authentication required for all endpoints
- Role-based authorization (admin role required for deletion)

## Getting Started

### Prerequisites
- Java 21 or higher
- Maven 3.6 or higher

### Running the Application
1. Clone this repository
   ```
   git clone https://github.com/yourusername/quick-poll.git
   ```
2. Navigate to the project directory
   ```
   cd quick-poll
   ```
3. Run the application
   ```
   mvn spring-boot:run
   ```
4. The application will be available at `http://localhost:8080`
5. Access the API documentation at `http://localhost:8080/swagger-ui.html`

## API Documentation

### Poll Endpoints

#### v1 API (Public)
- `GET /v1/polls` - Get all polls
- `POST /v1/polls` - Create a new poll
- `GET /v1/polls/{pollId}` - Get a specific poll
- `PUT /v1/polls/{pollId}` - Update a poll
- `DELETE /v1/polls/{pollId}` - Delete a poll

#### v2 API (Public)
- `GET /v2/polls?page=0&size=10` - Get polls with pagination
- `POST /v2/polls` - Create a new poll
- `GET /v2/polls/{pollId}` - Get a specific poll
- `PUT /v2/polls/{pollId}` - Update a poll
- `DELETE /v2/polls/{pollId}` - Delete a poll

#### v3 API (Authenticated)
- `GET /v3/polls?page=0&size=10` - Get polls with pagination (requires authentication)
- `POST /v3/polls` - Create a new poll (requires authentication)
- `GET /v3/polls/{pollId}` - Get a specific poll (requires authentication)
- `PUT /v3/polls/{pollId}` - Update a poll (requires authentication)
- `DELETE /v3/polls/{pollId}` - Delete a poll (requires ADMIN role)

### Vote Endpoints
- `POST /v1/polls/{pollId}/votes` - Cast a vote
- `GET /v1/polls/{pollId}/votes` - Get all votes for a poll

### Compute Results Endpoint
- `GET /v1/computeResult?pollId={pollId}` - Compute the results of a poll

## Example Usage

### Creating a Poll
```bash
curl -X POST http://localhost:8080/v1/polls \
  -H "Content-Type: application/json" \
  -d '{
    "question": "What is your favorite programming language?",
    "options": [
      {"value": "Java"},
      {"value": "Python"},
      {"value": "JavaScript"},
      {"value": "C#"}
    ]
  }'
```

### Casting a Vote
```bash
curl -X POST http://localhost:8080/v1/polls/1/votes \
  -H "Content-Type: application/json" \
  -d '{
    "option": {"id": 1}
  }'
```

### Getting Poll Results
```bash
curl -X GET http://localhost:8080/v1/computeResult?pollId=1
```

### Using v3 API with Authentication
```bash
curl -X GET http://localhost:8080/v3/polls \
  -u "admin:admin"
```

## Testing

The application includes both unit tests and integration tests:
- Unit tests: Test individual components in isolation
- Integration tests: Test the application as a whole

To run the tests:
```bash
mvn test
```

## License

This project is open source and available under the [MIT License](LICENSE).
