# Personal Task Management Application

## Overview

The Personal Task Management Application is a full-stack application designed to help users create, manage, and organize their daily tasks. The application offers features like user authentication, task categorization, deadlines, priority levels, and progress tracking. The backend is built with Java (Spring Boot), while the frontend uses React, providing a comprehensive experience in modern web development.

## Key Features

### User Authentication

- **Registration & Login**: Secure user registration and login using JWT (JSON Web Tokens).
- **Password Management**: Secure password hashing and validation.

### Task Management

- **CRUD Operations**: Full CRUD operations for tasks.
- **Categorization**: Assign categories or tags to tasks (e.g., Work, Personal, Urgent).
- **Deadlines & Reminders**: Set deadlines and receive notifications/reminders.
- **Priority Levels**: Assign priority (e.g., High, Medium, Low) to tasks. -To be added-
- **Progress Tracking**: Mark tasks as pending, in-progress, or completed. -To be added-

### User Dashboard

- **Task Overview**: Display tasks in list or Kanban board view. -To be added-
- **Statistics**: Show task completion rates, upcoming deadlines, etc. -To be added-
- **Search & Filters**: Search tasks and filter by category, priority, or status. -To be added-

### Responsive Frontend

- **React Components**: Modular and reusable components for scalability.
- **State Management**: Use Redux or Context API for state management.
- **Responsive Design**: Ensures usability across devices (desktop, tablet, mobile). -In progress-

### Backend API

- **RESTful Services**: Implement RESTful APIs using Spring Boot.
- **Database Integration**: Use a relational database (PostgreSQL) with JPA/Hibernate.
- **Security**: Implement security best practices (e.g., input validation, authorization). -In progress-

### Testing

- **Unit Tests**: Backend testing with JUnit and Mockito.
- **Integration Tests**: API endpoint testing with Spring Boot Test.
- **Frontend Testing**: Component testing with Jest and React Testing Library. -To be added-

## Technology Stack

### Backend

- **Language**: Java
- **Framework**: Spring Boot
- **Database**: PostgreSQL
- **ORM**: Hibernate/JPA
- **Security**: Spring Security with JWT
- **Testing**: JUnit, Mockito, Spring Boot Test

### Frontend

- **Library**: React
- **State Management**: Redux or Context API
- **Styling**: CSS3, Bootstrap or Material-UI
- **Routing**: React Router
- **Testing**: Jest, React Testing Library

### Tools

- **Version Control**: Git & GitHub
- **Build Tools**: Maven (Java), npm/yarn (React)
- **IDE**: IntelliJ IDEA, VS Code
- **Containerization (optional)**: Docker

Getting Started

Prerequisites

- Java 11 or higher
- Node.js and npm/yarn
- PostgreSQL

Backend Setup

1. Clone the repository:
   git clone https://github.com/your-username/task-manager.git
   cd task-manager/backend

2. Configure the database in application.properties:

spring.datasource.url=jdbc:postgresql://localhost:5432/taskdb
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
jwt.secret=your_secret_key

3. Build and run the backend application:
   ./mvnw spring-boot:run

Frontend Setup

1. Navigate to the frontend directory:
   cd task-manager/frontend

2. Install dependencies:
   npm install

3. Run the development server:
   npm start

Running Tests
Backend Tests:
cd task-manager/backend
./mvnw test
Frontend Tests:
cd task-manager/frontend
npm test

Deployment 1. Backend: Deploy the Spring Boot application to Heroku, AWS, or any other Java-supported cloud platform. 2. Frontend: Deploy the React application to Netlify, Vercel, or any other hosting service.

Contact:
For questions or suggestions, please open an issue or contact alexandru.b.iasi@gmail.com.
