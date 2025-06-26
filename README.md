# Quick Poll Application

A RESTful API for creating and managing polls built with Spring Boot.

## Overview

Quick Poll is a simple polling application that allows users to:
- Create polls with multiple options
- Vote on existing polls
- View poll results

## Technologies Used

- Java
- Spring Boot
- Spring Data JPA
- H2 Database (default)

## Project Structure

The application follows a standard Spring Boot project structure:
- `domain`: Contains entity classes (Poll, Option, Vote)
- `repository`: Contains Spring Data JPA repositories
- `controller`: Contains REST controllers

## Getting Started

### Prerequisites
- Java 8 or higher
- Maven

### Running the Application
1. Clone this repository
2. Navigate to the project directory
3. Run `mvn spring-boot:run`
4. The application will be available at `http://localhost:8080`

## API Endpoints

- `GET /polls` - Get all polls
- `POST /polls` - Create a new poll

## License

This project is open source and available under the [MIT License](LICENSE).