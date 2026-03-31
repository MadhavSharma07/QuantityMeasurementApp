# Quantity Measurement Application

A Java-based measurement conversion and arithmetic application built incrementally through 16 use cases (UC1–UC16). The project demonstrates enterprise-level Java development practices including generic programming, design patterns, N-Tier architecture, and JDBC database integration.

---

## Table of Contents

- [Project Overview](#project-overview)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Architecture](#architecture)
- [Measurement Categories](#measurement-categories)
- [Supported Operations](#supported-operations)
- [Use Case Summary](#use-case-summary)
- [Getting Started](#getting-started)
- [Running the Application](#running-the-application)
- [Running Tests](#running-tests)
- [Database Configuration](#database-configuration)
- [Design Patterns Used](#design-patterns-used)
- [SOLID Principles](#solid-principles)

---

## Project Overview

The Quantity Measurement Application supports equality comparison, unit conversion, and arithmetic operations (addition, subtraction, division) across multiple measurement categories. It is built with a clean N-Tier architecture and persists operation history to a database using JDBC.

---

## Features

- Compare quantities across different units (e.g. 1 foot == 12 inches)
- Convert between units within the same category
- Add, subtract, and divide quantities with cross-unit support
- Temperature equality and conversion (Celsius ↔ Fahrenheit)
- Arithmetic operations blocked for temperature (unsupported by design)
- Cross-category comparison prevention (e.g. length vs weight returns false)
- Persistent operation history stored in H2 (dev) or MySQL (production)
- Switchable repository: in-memory cache or database via factory method
- Custom exception hierarchy for domain and database errors

---

## Tech Stack

| Technology     | Purpose                          |
|----------------|----------------------------------|
| Java 17        | Core language                    |
| Maven          | Build tool and dependency management |
| H2 Database    | In-memory database for dev/test  |
| MySQL          | Production database (optional)   |
| JDBC           | Database connectivity            |
| JUnit 4        | Unit and integration testing     |
| SLF4J/Logback  | Logging (UC16+)                  |

---

## Project Structure

```
quantity-measurement/
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/com/app/quantitymeasurement/
│   │   │   ├── QuantityMeasurementApp.java          # Entry point
│   │   │   ├── controller/
│   │   │   │   └── QuantityMeasurementController.java
│   │   │   ├── service/
│   │   │   │   ├── IQuantityMeasurementService.java
│   │   │   │   └── QuantityMeasurementServiceImpl.java
│   │   │   ├── repository/
│   │   │   │   ├── IQuantityMeasurementRepository.java
│   │   │   │   ├── QuantityMeasurementCacheRepository.java
│   │   │   │   └── QuantityMeasurementDatabaseRepository.java
│   │   │   ├── entity/
│   │   │   │   ├── QuantityDTO.java
│   │   │   │   └── QuantityMeasurementEntity.java
│   │   │   ├── unit/
│   │   │   │   ├── IMeasurable.java
│   │   │   │   ├── Quantity.java
│   │   │   │   ├── LengthUnit.java
│   │   │   │   ├── WeightUnit.java
│   │   │   │   ├── VolumeUnit.java
│   │   │   │   └── TemperatureUnit.java
│   │   │   └── exception/
│   │   │       ├── QuantityMeasurementException.java
│   │   │       └── DatabaseException.java
│   │   └── resources/
│   │       └── db/
│   │           └── schema.sql
│   └── test/
│       └── java/com/app/quantitymeasurement/
│           └── repository/
│               └── QuantityMeasurementDatabaseRepositoryTest.java
```

---

## Architecture

The application follows a **4-Tier N-Tier architecture**:

```
QuantityMeasurementApp  (Application Layer)
        │
        ▼
QuantityMeasurementController  (Controller Layer)
        │
        ▼
QuantityMeasurementServiceImpl  (Service Layer)
        │
        ▼
IQuantityMeasurementRepository  (Repository Layer)
   ├── QuantityMeasurementCacheRepository   (in-memory)
   └── QuantityMeasurementDatabaseRepository  (JDBC/H2/MySQL)
```

**Data flow per operation:**

```
Controller receives QuantityDTO
    → Service validates and converts to Quantity<U>
    → Service performs operation using Quantity class
    → Service saves QuantityMeasurementEntity to Repository
    → Service returns result as QuantityDTO
    → Controller displays result
```

---

## Measurement Categories

| Category    | Units Supported                              | Arithmetic |
|-------------|----------------------------------------------|------------|
| Length      | FEET, INCHES, YARDS, CENTIMETER              | ✅ Full     |
| Weight      | GRAM, KILOGRAM, MILLIGRAM, POUND, TONNE      | ✅ Full     |
| Volume      | LITRE, MILLILITRE, GALLON                    | ✅ Full     |
| Temperature | CELSIUS, FAHRENHEIT                          | ❌ Compare + Convert only |

---

## Supported Operations

| Operation   | Description                               | Example                          |
|-------------|-------------------------------------------|----------------------------------|
| Compare     | Check if two quantities are equal         | 1 ft == 12 in → true             |
| Convert     | Convert to a different unit               | 100°C → 212°F                    |
| Add         | Sum two quantities                        | 1 kg + 500 g = 1.5 kg            |
| Subtract    | Difference between two quantities         | 10 ft − 6 in = 9.5 ft            |
| Divide      | Ratio between two quantities (scalar)     | 10 ft ÷ 2 ft = 5.0               |

---

## Use Case Summary

| UC   | Description                                                    |
|------|----------------------------------------------------------------|
| UC1  | Feet equality comparison                                       |
| UC2  | Inches equality comparison                                     |
| UC3  | Feet ↔ Inches cross-unit comparison                           |
| UC4  | Yards support                                                  |
| UC5  | Centimeter support                                             |
| UC6  | Unit conversion (feet → inches, yards → feet, etc.)           |
| UC7  | Addition with same units                                       |
| UC8  | Addition with cross units and explicit target unit             |
| UC9  | Weight measurement (gram, kilogram, tonne)                     |
| UC10 | Generic `Quantity<U>` class + `IMeasurable` interface          |
| UC11 | Volume measurement (litre, millilitre, gallon)                 |
| UC12 | Subtraction and division operations                            |
| UC13 | DRY refactor — centralized `ArithmeticOperation` enum          |
| UC14 | Temperature with selective arithmetic (UC + ISP refactor)      |
| UC15 | N-Tier architecture (Controller / Service / Repository layers) |
| UC16 | JDBC database persistence with H2 and Maven build              |

---

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

Verify your setup:

```bash
java -version
mvn -version
```

### Clone and Build

```bash
git clone <your-repo-url>
cd quantity-measurement
mvn clean compile
```

---

## Running the Application

```bash
mvn exec:java
```

This runs `QuantityMeasurementApp.main()` which demonstrates all operations across all measurement categories and prints the operation history from the database.

**To switch between repositories**, edit the `createRepository()` method in `QuantityMeasurementApp.java`:

```java
// Use H2 in-memory database (default)
return new QuantityMeasurementDatabaseRepository();

// Use in-memory cache instead
return QuantityMeasurementCacheRepository.getInstance();
```

---

## Running Tests

```bash
# Run all tests
mvn clean test

# Run only database repository tests
mvn test -Dtest=QuantityMeasurementDatabaseRepositoryTest
```

---

## Database Configuration

### H2 (Default — Development/Test)

No setup required. H2 runs in-memory automatically. The schema is created on startup via `initSchema()` inside `QuantityMeasurementDatabaseRepository`.

### MySQL (Production)

1. Create the database:

```sql
CREATE DATABASE quantitydb;
```

2. Run the schema:

```bash
mysql -u root -p quantitydb < src/main/resources/db/schema.sql
```

3. Update the constructor call in `QuantityMeasurementApp.java`:

```java
return new QuantityMeasurementDatabaseRepository(
    "jdbc:mysql://localhost:3306/quantitydb?useSSL=false",
    "root",
    "yourpassword"
);
```

4. Uncomment the MySQL dependency in `pom.xml`:

```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.33</version>
</dependency>
```

---

## Design Patterns Used

| Pattern              | Where Applied                                      |
|----------------------|----------------------------------------------------|
| Generic Programming  | `Quantity<U extends IMeasurable>`                  |
| Factory              | `createRepository()` in `QuantityMeasurementApp`   |
| Singleton            | `QuantityMeasurementCacheRepository.getInstance()` |
| Facade               | `QuantityMeasurementController`                    |
| Dependency Injection | Repository injected into Service via constructor   |
| Strategy (Enum)      | `ArithmeticOperation` enum with lambda compute()   |
| Template Method      | `IMeasurable` default methods                      |

---

## SOLID Principles

| Principle                       | How Applied                                                             |
|---------------------------------|-------------------------------------------------------------------------|
| Single Responsibility (SRP)     | Each class has one job — Controller routes, Service operates, Repository persists |
| Open/Closed (OCP)               | New unit enums added without modifying `Quantity<U>` or service logic   |
| Liskov Substitution (LSP)       | Any `IMeasurable` implementation works seamlessly with `Quantity<U>`    |
| Interface Segregation (ISP)     | `TemperatureUnit` opts out of arithmetic via `validateOperationSupport` |
| Dependency Inversion (DIP)      | Service and Controller depend on interfaces, not concrete classes       |
