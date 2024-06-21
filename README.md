# HotelWebServer Application

HotelWebServer is a Java Spring Boot application designed to manage various aspects of a hotel, including room booking, employee management, housekeeping tasks, and room managment.

## Functionality

HotelWebServer provides comprehensive functionality for managing hotel operations:

### Authentication

Secure login and registration functionalities using JWT (JSON Web Token) for user authentication.

### Booking Management

Allows users to:
- Create, cancel, check-in, and check-out bookings.
- Retrieve booking details and history.
- Manage bookings within date ranges.

### Employee Management

Facilitates:
- Adding and deleting employees.
- Permissions restricted to administrators.

### Housekeeping Task Management

Enables:
- Assigning rooms to employees for cleaning tasks.
- Marking tasks as finished.
- Retrieving tasks by employee.

### Room Management

Provides functionalities to:
- Add, update, and delete rooms.
- Retrieve rooms by status, price range, capacity, class, and availability within specified dates.

## Technologies Used

- **Java**: Core programming language for application logic.
- **Spring Boot**: Framework for building enterprise-level Java applications with ease.
- **Spring Security**: Provides authentication and authorization support.
- **Spring Data JPA**: Simplifies database operations using Hibernate as the JPA provider.
- **MySQL**: Relational database management system for data persistence. Hibernate will auto-generate tables based on entity mappings.
- **Swagger**: API documentation tool for easy exploration of endpoints.
- **Maven**: Dependency management tool.
- **Git**: Version control system for collaboration and code management.

## Getting Started

To run the application locally, follow these steps:

1. **Clone the repository:**

   ```bash
   git clone https://github.com/YourRepository/HotelWebServer.git
   cd HotelWebServer


2. **Set up MySQL Database:**
  1 - Create a MySQL database named hotel_db. You can do this through your MySQL administration tool or command line.
   
  2 - Configure the database username and password in your application.properties file. This file is typically located in the src/main/resources directory of your project.
   
4. **Build and Run the Application:**
    ```bash
      mvn clean package
      java -jar target/HotelWebServer-0.0.1-SNAPSHOT.jar
    ```
## Explore the API:
 - **Access the API documentation and test endpoints using Swagger at http://localhost:8080/swagger-ui.html.**
 - **Detailed API endpoints and functionalities are documented in Swagger UI. Use the Swagger UI to interact with the API endpoints effectively.**

## Error Handling:

### Global Exception Handling and Exception Handler

- **Global Exception Handling**: Utilized global exception handling throughout the application to manage and process exceptions uniformly.
  
- **Exception Handler**: Implemented an exception handler to return appropriate HTTP status codes and structured error responses based on the encountered exceptions.

### Custom Exceptions

- **Custom Exceptions**: Defined specific exception classes (`IllegalArgumentException`, `ExpiredJwtException`, etc.) to handle various error scenarios effectively, ensuring clarity and specificity in error reporting.

### Handling Security Exceptions

- **Handling Security Exceptions**: Managed security exceptions such as authentication failures (`AuthenticationException`) and authorization issues using dedicated components like `JwtAuthEntryPoint` and `JwtAuthenticationFilter`. These components ensure secure handling and response to security-related errors.


### DTO for Exception Response

- **DTO for Exception Response**: Employed Data Transfer Objects (DTOs) to format and standardize error responses. These DTOs encapsulate error details for consistent and informative communication with clients.

## Security

HotelWebServer implements robust security measures using JWT-based authentication and role-based access control (admin and user roles) to safeguard endpoints and manage user permissions effectively.

- **JWT-Based Authentication**: Utilizes JSON Web Tokens (JWT) for secure authentication of users. Upon successful authentication, users receive a token that must be included in subsequent requests for access.

- **Role-Based Access Control**: Enforces role-based access control to restrict endpoint access based on user roles (admin vs. user). Administrators have access to privileged operations, while regular users have restricted access.

- **Exception Handling**: Implements global exception handling to manage and respond to authentication and authorization errors uniformly. Custom exception handling ensures that appropriate HTTP status codes and error messages are returned to clients.

- **Security Components**:
  - **JwtAuthenticationFilter**: Intercepts incoming requests to validate JWT tokens. If valid, sets the authenticated user in the Security Context.
  - **JwtAuthEntryPoint**: Handles authentication failures by returning an HTTP 401 Unauthorized error with relevant error messages.
  - **Token Provider**: Generates and validates JWT tokens used for authentication. Ensures tokens are signed securely and have not expired before processing requests.

This setup ensures that HotelWebServer is secure against unauthorized access attempts and provides a reliable mechanism for managing user sessions and permissions across the application.


## Entity-Relationship Diagram (ERD)

![ERD](https://github.com/itsomar278/HotelManagment/blob/master/erd.png)


## Contributors

[Omar Badawi](https://github.com/itsomar278) .
