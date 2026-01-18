# Car Reservation System: Technical Assessment Review

## 1. OO Design Principles

**Structure:**
- `domain/CarType.java`, `Car.java`, `Reservation.java`, `TimeWindow.java`: Cleanly model car, type, reservation window, and business logic.
- `application/AllocationStrategy.java`, `LowestIdAllocationStrategy.java`, `ReservationService.java`: All orchestration logic lives in the service, allocation is pluggable with an interface.
- `infrastructure/InMemoryCarInventory.java`, `InMemoryReservationRepository.java`: Inventory and repository are abstracted for future extension to DB or external API.

**Strengths:**
- Clear domain/service/infrastructure boundaries.
- Domain model is immutable where appropriate (e.g., Car, Reservation).
- Service is extensible: easily add pricing, user, or validation rules.
- Strategy pattern for allocation logic.

**Deficiencies (opportunities):**
- **No persistent storage:** Only in-memory repository, easy to upgrade to JPA/DB.
- **No thread-safety:** Not needed for test/assessment, but add for production (synchronize access to inventory/repo).
- **No REST API:** All logic is local/Java; not callable remotely.
- **No user/customer concept:** Could add multi-user, authentication, or bookings per user.
- **No pricing/payments:** Add price/rate/fee calculation if desired.
- **Limited input validation:** Could enforce stricter validation in service (e.g., non-past dates, future proofing edge cases).

## 2. Unit Test Coverage

- Test classes exist in `src/test/java/com/statestreet/carrental/domain`, `application`, `infrastructure` covering:
  - Reservation logic
  - TimeWindow overlaps
  - AllocationStrategy selection and prioritization
  - Inventory limit enforcement and reservation persistence

**Gap:**  
- No true end-to-end/integration tests (multi-service or REST).
- More tests for error/edge paths (e.g., duplicate id, reservation modifications, concurrent booking) could be added.

## 3. Extensibility and Interview Scalability

**Very Strong:**  
- To add a REST API, implement a controller/environment (Spring Boot, Jakarta, or another HTTP framework).
- Easily adapt to DB or external systems by swapping out repository/inventory implementations.
- Allocate by pricing, availability, or user priority by adding subclasses of AllocationStrategy.
- To add features like pricing, user management, reporting, or async notifications: plug new classes into existing domain/service patterns.
- Fully testable and understandable for follow-up interview prompts.

## 4. Gaps and Next Steps

**If interviewer asks for enhancement, suggest or demo:**
- Adding car search/browse by multi-criteria.
- Add user/customer object and assign reservations to specific people.
- Add pricing engine, discount policies, or dynamic rates.
- Extend repo to persistent DB (JPA, JDBC, document store).
- Implement RESTful API/GraphQL API to allow UI or client access.
- Add email notification or SMS for booking.

**Conclusion:**  
This backend meets all assessment objectives and is extensible and robust for both next-step questions and real-world scenarios. The design and tests are clean, modular, and clearly documented.
