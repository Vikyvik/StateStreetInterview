package com.statestreet.carrental.application;

import com.statestreet.carrental.domain.Car;
import com.statestreet.carrental.domain.CarType;
import com.statestreet.carrental.domain.Reservation;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LowestIdAllocationStrategyTest {
    private List<Car> cars() {
        return List.of(
                new Car("A", CarType.SEDAN),
                new Car("B", CarType.SEDAN),
                new Car("C", CarType.SEDAN)
        );
    }

    @Test
    void allocatesLowestIdCarWhenAllAvailable() {
        LowestIdAllocationStrategy strategy = new LowestIdAllocationStrategy();
        Car allocated = strategy.allocate(cars(), List.of(),
                LocalDateTime.of(2023, 1, 1, 10, 0),
                LocalDateTime.of(2023, 1, 1, 12, 0)
        );
        assertEquals("A", allocated.getId());
    }

    @Test
    void deterministicAllocation() {
        LowestIdAllocationStrategy strategy = new LowestIdAllocationStrategy();
        Car before = strategy.allocate(cars(), List.of(),
                LocalDateTime.of(2023, 1, 1, 17, 0),
                LocalDateTime.of(2023, 1, 1, 19, 0)
        );
        Car after = strategy.allocate(cars(), List.of(),
                LocalDateTime.of(2023, 1, 1, 17, 0),
                LocalDateTime.of(2023, 1, 1, 19, 0)
        );
        assertEquals(before.getId(), after.getId());
    }

    @Test
    void avoidsReservedCars() {
        LowestIdAllocationStrategy strategy = new LowestIdAllocationStrategy();
        Car carA = cars().get(0);
        Car carB = cars().get(1);
        Car carC = cars().get(2);

        List<Reservation> reservations = new ArrayList<>();
        // Reserve car A for the requested slot
        reservations.add(new Reservation(
                "r1", carA.getId(), CarType.SEDAN,
                LocalDateTime.of(2023, 1, 1, 10, 0),
                LocalDateTime.of(2023, 1, 1, 12, 0)
        ));

        Car allocated = strategy.allocate(
                cars(), reservations,
                LocalDateTime.of(2023, 1, 1, 11, 0),
                LocalDateTime.of(2023, 1, 1, 13, 0)
        );
        // Should pick B since A is booked
        assertEquals("B", allocated.getId());

        // Also reserve B, so only C is left
        reservations.add(new Reservation(
                "r2", carB.getId(), CarType.SEDAN,
                LocalDateTime.of(2023, 1, 1, 11, 0),
                LocalDateTime.of(2023, 1, 1, 13, 0)
        ));

        allocated = strategy.allocate(
                cars(), reservations,
                LocalDateTime.of(2023, 1, 1, 11, 0),
                LocalDateTime.of(2023, 1, 1, 13, 0)
        );
        assertEquals("C", allocated.getId());

        // Book all, should return null
        reservations.add(new Reservation(
                "r3", carC.getId(), CarType.SEDAN,
                LocalDateTime.of(2023, 1, 1, 11, 0),
                LocalDateTime.of(2023, 1, 1, 13, 0)
        ));

        allocated = strategy.allocate(
                cars(), reservations,
                LocalDateTime.of(2023, 1, 1, 11, 0),
                LocalDateTime.of(2023, 1, 1, 13, 0)
        );
        assertNull(allocated);
    }
}
