# Car Rental System – Technical Assessment

This project is a minimal, object-oriented simulation of a car rental system, designed for a technical assessment.

## Features

- **Reserve a car by type**: Supports Sedan, SUV, and Van.
- **Inventory limit**: Each car type has a fixed, limited inventory.
- **Reservation logic**: Reserve for a specific start date/time and number of days, with overlap and inventory rules enforced.
- **In-memory design**: No database or external dependencies.
- **Comprehensive unit tests**: Validates all business requirements and edge cases.

## Getting Started

### Requirements
- Java 17+
- Maven

### Build and Run Tests

```sh
cd backend/car-rental
mvn clean test
```

All logic lives in `ReservationService.java` and is thoroughly exercised in `ReservationServiceTest.java`.

## Project Structure

- `src/main/java/com/statestreet/carrental/domain/` &mdash; Core data models (Car, CarType, Reservation, etc.)
- `src/main/java/com/statestreet/carrental/application/ReservationService.java` &mdash; Central business logic
- `src/main/java/com/statestreet/carrental/application/ErrorMessages.java` &mdash; Centralized error messages
- `src/test/java/com/statestreet/carrental/application/ReservationServiceTest.java` &mdash; Unit tests

## Extensibility

- The inventory can be extended dynamically (see the `addInventory` method in `ReservationService`).
- Architecture is ready for extension (e.g., new car types, enhancements).

## Notes

- No REST API or UI is provided; the system is intended for code-level evaluation.
- All important logic and test coverage for requirements are present.
