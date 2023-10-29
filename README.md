# Airline Reservation System

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen.svg)](#)
[![Coverage Status](https://img.shields.io/badge/coverage-85%25-green.svg)](#)

---

## Overview

The Airline Reservation System is a robust desktop Java application for managing airline bookings, flights, and passenger information through an intuitive Swing GUI. It employs a layered architecture with comprehensive data validation, connection pooling, and SQL injection protection. The system uses SQLite for persistent storage and provides a complete solution for airline management operations.

**Features:**
- Flight management (add, update, delete, search, view)
- Passenger management with validation and secure data handling
- Booking creation and management with business rule enforcement
- Connection pooling for efficient database access
- Comprehensive exception handling and logging
- Service-oriented architecture with dependency injection

**Architecture:**  
Java Swing (Desktop UI) → Service Layer → DAO Layer → SQLite DB (local file)  
Implements DTO pattern for data transfer between layers.

---

## Table of Contents

- [Repository Structure](#repository-structure)
- [Tech Stack](#tech-stack)
- [Quick Start](#quick-start)
- [Installation & Setup](#installation--setup)
- [Configuration](#configuration)
- [Running the App](#running-the-app)
- [Usage](#usage)
- [Testing](#testing)
- [Security](#security)
- [Performance & Scaling](#performance--scaling)
- [Observability](#observability)
- [Troubleshooting & FAQ](#troubleshooting--faq)


---

## Repository Structure

```
├── src/
│   ├── airline/reservation/system/  # UI components
│   ├── config/                      # Configuration classes
│   │   ├── AppConfig.java           # Application configuration
│   │   └── LoggingConfig.java       # Logging configuration
│   ├── dao/                         # Data Access Objects
│   │   ├── DBConnectionManager.java # Connection pooling
│   │   ├── FlightDao.java           # Flight data operations
│   │   ├── PassengerDao.java        # Passenger data operations
│   │   └── BookingDao.java          # Booking data operations
│   ├── dto/                         # Data Transfer Objects
│   ├── model/                       # Domain models
│   ├── service/                     # Business logic
│   │   ├── exception/               # Service exceptions
│   │   └── ServiceFactory.java      # Service dependency injection
│   └── ui/                          # UI components
├── test/                            # Test classes
├── application.properties           # Application configuration
└── airlineDB.db                     # SQLite database
```

## Tech Stack

- **Language:** Java 8+
- **UI Framework:** Java Swing
- **Database:** SQLite
- **Build System:** Apache Ant
- **Libraries:**
  - TimingFramework (UI animations)
  - SQLite JDBC Driver
- **Architecture:** Layered (UI → Service → DAO → Database)
- **Design Patterns:** DAO, DTO, Factory, Singleton

## Quick Start

1. Ensure Java 8 or higher is installed:
   ```bash
   java -version
   ```

2. Clone the repository:

   ```

3. Run the application:
   ```bash
   # On Windows
   java -jar dist/Airline_Reservation_System.jar

   # On macOS/Linux
   java -jar dist/Airline_Reservation_System.jar
   ```

4. Login with default credentials:
   - Username: ``
   - Password: ``

## Installation & Setup

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Apache Ant (for building from source)
- IDE: NetBeans (recommended), Eclipse, or IntelliJ IDEA

### Building from Source

1. Clone the repository:

   ```

2. Build with Ant:
   ```bash
   ant clean compile jar
   ```

3. The built JAR will be available in the `dist/` directory.

### Database Setup

The application automatically creates and initializes the SQLite database file (`airlineDB.db`) if it doesn't exist. No additional database setup is required.

## Configuration

The application uses `application.properties` for configuration:

| Name | Type | Default | Required | Description |
|------|------|---------|----------|-------------|
| db.path | String | airlineDB.db | Yes | Path to SQLite database file |
| db.user | String | | No | Database username (if needed) |
| db.password | String | | No | Database password (if needed) |
| admin.user | String | zabairline | Yes | Admin username |
| admin.password | String | admin123 | Yes | Admin password |
| log.level | String | INFO | No | Logging level (INFO, WARNING, SEVERE) |
| log.file | String | airline.log | No | Log file path |

To override configuration, edit the `application.properties` file in the application root directory.

## Running the App

### Development Mode

1. Open the project in NetBeans IDE:
   ```
   File > Open Project > [select project directory]
   ```

2. Run the project:
   ```
   Right-click project > Run
   ```

### Production Mode

Run the compiled JAR file:

```bash
java -jar dist/Airline_Reservation_System.jar
```

The application will display a loader screen followed by the login page.

## Usage

### Login

1. Launch the application
2. Enter admin credentials:
   - Username: ``
   - Password: ``
3. Click "Login"

### Flight Management

- **View Flights:** Click "FLIGHTS" on the main dashboard
- **Add Flight:** In the Flights page, fill in flight details and click "Add"
- **Update Flight:** Select a flight from the table, modify details, and click "Update"
- **Delete Flight:** Select a flight and click "Delete"
- **Search Flights:** Enter search criteria and click "Search"

### Passenger Management

- **View Passengers:** Click "PASSENGERS" on the main dashboard
- **Add Passenger:** Fill in passenger details and click "Add"
- **Update Passenger:** Select a passenger, modify details, and click "Update"
- **Delete Passenger:** Select a passenger and click "Delete"

### Booking Management

- **View Bookings:** Click "BOOKINGS" on the main dashboard
- **Create Booking:** Fill in booking details and click "Book"
- **Update Booking:** Select a booking, modify details, and click "Update"
- **Cancel Booking:** Select a booking and click "Cancel"

## Testing

The project includes unit tests for the DAO layer. To run tests:

```bash
# Using Ant
ant test

# Manually
java -cp lib/*:dist/Airline_Reservation_System.jar org.junit.runner.JUnitCore test.dao.FlightDaoTest
```

Test classes are located in the `test/` directory and follow the naming convention `*Test.java`.

## Security

The application implements several security measures:

- **SQL Injection Protection:** All database queries use prepared statements
- **Input Validation:** Service layer validates all inputs before processing
- **Exception Handling:** Comprehensive exception hierarchy with proper logging
- **Authentication:** Password-protected admin access
- **Resource Management:** Proper connection handling and resource cleanup

### Security Checklist

- [x] SQL Injection Protection
- [x] Input Validation
- [x] Authentication
- [x] Exception Handling
- [x] Resource Management
- [ ] Password Encryption (TBD)
- [ ] Role-Based Access Control (TBD)

## Performance & Scaling

The application implements connection pooling to improve database access performance. The connection pool is configured with:

- Initial pool size: 5 connections
- Maximum pool size: 10 connections
- Connection validation before use

For larger deployments, consider:
- Increasing the connection pool size
- Migrating to a more robust database (MySQL, PostgreSQL)
- Implementing caching for frequently accessed data

## Observability

### Logging

The application uses Java's built-in logging framework with centralized configuration:

- Log levels: SEVERE, WARNING, INFO, FINE
- Log outputs: Console and file (airline.log)
- Log format: [TIMESTAMP] [LEVEL] [CLASS] - Message

To adjust logging:
1. Modify `log.level` in `application.properties`
2. Or programmatically via `LoggingConfig.setLogLevel()`

## Troubleshooting & FAQ

### Common Issues

1. **Application fails to start**
   - Ensure Java 8+ is installed
   - Check file permissions on the JAR and database file
   - Verify the database file is not corrupted

2. **Database connection errors**
   - Ensure the database file path is correct in `application.properties`
   - Check if the database file is accessible and not locked by another process

3. **Login issues**
   - Default credentials: Username `zabairline`, Password `admin123`
   - If modified, check `application.properties` for current values

4. **UI rendering problems**
   - Ensure Java is up to date
   - Try running with `-Dsun.java2d.d3d=false` flag

### Error Codes

- `DB001`: Database connection error
- `DB002`: SQL query execution error
- `SVC001`: Validation error
- `SVC002`: Resource not found
- `SVC003`: Business rule violation


