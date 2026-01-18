package com.statestreet.carrental.application;

import com.statestreet.carrental.domain.Car;
import com.statestreet.carrental.domain.CarType;
import com.statestreet.carrental.domain.Reservation;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Minimal reservation service: in-memory inventory, reservation storage, and allocation logic.
 */
public class ReservationService {
    private static final String SEDAN1_ID = "SEDAN-1";
    private static final String SEDAN2_ID = "SEDAN-2";
    private static final String SUV1_ID   = "SUV-1";
    private static final String SUV2_ID   = "SUV-2";
    private static final String VAN1_ID   = "VAN-1";

    private static final String ERR_DAYS = "days must be > 0";
    private static final String ERR_PAST = "start may not be in the past";
    private static final String ERR_NO_CAR = "No available car for requested time window";
    private static final String RES_PREFIX = "RES-";

    private final Map<CarType, List<Car>> carInventory = new EnumMap<>(CarType.class);
    private final List<Reservation> reservations = new ArrayList<>();
    private final Clock clock;

    public ReservationService(Clock clock) {
        this.clock = Objects.requireNonNull(clock);
        // Minimal static inventory: 2 Sedans, 2 SUVs, 1 Van (can change as needed)
        carInventory.put(CarType.SEDAN, Arrays.asList(
            new Car(SEDAN1_ID, CarType.SEDAN), new Car(SEDAN2_ID, CarType.SEDAN)
        ));
        carInventory.put(CarType.SUV, Arrays.asList(
            new Car(SUV1_ID, CarType.SUV), new Car(SUV2_ID, CarType.SUV)
        ));
        carInventory.put(CarType.VAN, Collections.singletonList(
            new Car(VAN1_ID, CarType.VAN)
        ));
    }

    /**
     * Attempts to reserve a car of given type for a period of days starting at start.
     * @throws IllegalArgumentException if days <= 0, or start is in the past, or if unavailable
     */
    public Reservation reserve(CarType type, LocalDateTime start, int days) {
        if (days <= 0) throw new IllegalArgumentException(ERR_DAYS);
        LocalDateTime now = LocalDateTime.now(clock);
        if (start.isBefore(now)) throw new IllegalArgumentException(ERR_PAST);

        LocalDateTime end = start.plusDays(days);
        List<Car> cars = carInventory.getOrDefault(type, Collections.emptyList());

        Car allocated = findAvailableCar(cars, type, start, end);
        if (allocated == null)
            throw new IllegalArgumentException(ERR_NO_CAR);

        Reservation newRes = new Reservation(
                generateReservationId(),
                allocated.getId(),
                type,
                start,
                end
        );
        reservations.add(newRes);
        return newRes;
    }

    private Car findAvailableCar(List<Car> cars, CarType type, LocalDateTime start, LocalDateTime end) {
        for (Car car : cars) {
            boolean available = reservations.stream()
                    .filter(r -> r.getCarId().equals(car.getId()))
                    .noneMatch(r -> overlaps(r.getStartDateTime(), r.getEndDateTime(), start, end));
            if (available) return car;
        }
        return null;
    }

    // Returns true if either reservation overlaps the other.
    private boolean overlaps(LocalDateTime aStart, LocalDateTime aEnd, LocalDateTime bStart, LocalDateTime bEnd) {
        if (aStart.equals(bStart)) return true;
        if (aEnd.equals(bStart) || bEnd.equals(aStart)) return false;
        return bStart.isBefore(aEnd) && bEnd.isAfter(aStart);
    }

    // Simple id generator for demonstration purposes
    private String generateReservationId() {
        return RES_PREFIX + System.currentTimeMillis() + "-" + (int)(Math.random() * 1000);
    }

    // For minimal test visibility (could be removed)
    public List<Reservation> getAllReservations() {
        return Collections.unmodifiableList(reservations);
    }
}
