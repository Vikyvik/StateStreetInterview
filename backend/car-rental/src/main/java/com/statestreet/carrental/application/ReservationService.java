package com.statestreet.carrental.application;

import com.statestreet.carrental.domain.Car;
import com.statestreet.carrental.domain.CarType;
import com.statestreet.carrental.domain.Reservation;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import com.statestreet.carrental.infrastructure.InMemoryReservationRepository;
import com.statestreet.carrental.infrastructure.InMemoryCarInventory;

/**
 * Handles reservation booking logic.
 */
public class ReservationService {
    private final InMemoryCarInventory carInventory;
    private final InMemoryReservationRepository reservationRepo;
    private final AllocationStrategy allocationStrategy;
    private final Clock clock;

    public ReservationService(
            InMemoryCarInventory carInventory,
            InMemoryReservationRepository reservationRepo,
            AllocationStrategy allocationStrategy,
            Clock clock
    ) {
        this.carInventory = Objects.requireNonNull(carInventory);
        this.reservationRepo = Objects.requireNonNull(reservationRepo);
        this.allocationStrategy = Objects.requireNonNull(allocationStrategy);
        this.clock = Objects.requireNonNull(clock);
    }

    /**
     * Attempts to reserve a car of given type for a period of days starting at start.
     * @throws IllegalArgumentException if days <= 0, or start is in the past, or if unavailable
     */
    public Reservation reserve(CarType type, LocalDateTime start, int days) {
        if (days <= 0) throw new IllegalArgumentException("days must be > 0");
        LocalDateTime now = LocalDateTime.now(clock);
        if (start.isBefore(now)) throw new IllegalArgumentException("start may not be in the past");

        LocalDateTime end = start.plusDays(days);
        List<Car> cars = carInventory.getCarsByType(type);
        List<Reservation> reservations = reservationRepo.findAll();

        Car allocated = allocationStrategy.allocate(
                cars,
                reservations.stream()
                    .filter(r -> r.getType() == type)
                    .toList(),
                start,
                end
        );
        if (allocated == null)
            throw new IllegalArgumentException("No available car for requested time window");

        Reservation newRes = new Reservation(
            generateReservationId(),
            allocated.getId(),
            type,
            start,
            end
        );
        reservationRepo.save(newRes);
        return newRes;
    }

    // Simple id generator for demonstration purposes
    private String generateReservationId() {
        return "RES-" + System.currentTimeMillis() + "-" + (int)(Math.random() * 1000);
    }
}
