# Movie Booking System

A Spring Boot-based movie booking system with JWT authentication and role-based access control.

## Technical Stack

- Java 17
- Spring Boot 3.2.3
- Spring Security 6
- Spring Data JPA
- MySQL 8
- JWT for authentication
- Maven

## Prerequisites

- JDK 17 or later
- Maven 3.x
- MySQL 8.x
- IDE (IntelliJ IDEA or VS Code recommended)

## Setup Instructions

1. Clone the repository
2. Configure MySQL:
   - Create a database named `movie_booking` (or it will be created automatically)
   - Update database credentials in `application.properties` if needed

3. Build the project:
   ```bash
   mvn clean install
   ```

4. Run the application:
   ```bash
   mvn spring:boot run
   ```

## API Endpoints

### Authentication
- POST `/users/register` - Register a new user
- POST `/users/login` - Login and get JWT token

### Movies
- POST `/movies` - Add a new movie (ADMIN only)
- GET `/movies` - List all movies
- GET `/movies/{id}/seats` - View available seats for a movie

### Bookings
- POST `/movies/book` - Book seats for a movie
- POST `/bookings/{id}/pay` - Simulate payment for a booking
- GET `/bookings/{id}` - View booking details
- DELETE `/bookings/{id}` - Cancel a booking
- GET `/user/bookings` - View user's booking history

## Security

- JWT-based authentication
- Role-based access control (USER and ADMIN roles)
- Password hashing with BCrypt
- Stateless REST API

## Database Schema

The system uses the following main entities:
- User
- Movie
- Seat
- Booking
- UserRoles
- BookingSeats

## Development

The project follows standard Spring Boot conventions:
- Controllers in `com.moviebooking.controller`
- Services in `com.moviebooking.service`
- Repositories in `com.moviebooking.repository`
- Models in `com.moviebooking.model`
- Security in `com.moviebooking.security`

## Testing

Run tests with:
```bash
mvn test
```

## Docker Support

To build and run with Docker:
```bash
docker build -t movie-booking-system .
docker run -p 8080:8080 movie-booking-system
``` 