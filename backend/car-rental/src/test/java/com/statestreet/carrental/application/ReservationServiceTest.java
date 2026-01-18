package com.statestreet.carrental.application;

import com.statestreet.carrental.domain.CarType;
import com.statestreet.carrental.domain.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {
    private ReservationService service;
    private static final LocalDateTime BASE = LocalDateTime.of(2023, 1, 10, 10, 0);
    private static final Clock CLOCK = Clock.fixed(
            BASE.atZone(ZoneId.systemDefault()).toInstant(),
            ZoneId.systemDefault()
    );

    @BeforeEach
    void setUp() {
        service = new ReservationService(CLOCK);
    }

    @Test
    void successfulBookingReturnsReservation() {
        Reservation res = service.reserve(CarType.SEDAN, BASE.plusDays(1), 1);
        assertNotNull(res);
        assertEquals(CarType.SEDAN, res.getType());
        assertEquals(BASE.plusDays(1), res.getStartDateTime());
        assertEquals(BASE.plusDays(2), res.getEndDateTime());
        assertEquals(1, service.getAllReservations().size());
    }

    @ParameterizedTest
    @EnumSource(value = CarType.class, names = {"SEDAN", "SUV"})
    void inventoryLimitEnforced(CarType type) {
        // Both Sedan and SUV have 2 cars
        service.reserve(type, BASE.plusDays(1), 1);
        service.reserve(type, BASE.plusDays(1), 1);

        // Third attempt for the same slot should fail
        assertThrows(IllegalArgumentException.class, () ->
                service.reserve(type, BASE.plusDays(1), 1)
        );
    }

    @Test
    void addInventoryAllowsNewReservation() {
        // Add a new VAN to inventory, now two should be possible
        service.addInventory(CarType.VAN, "VAN-EXTRA");
        service.reserve(CarType.VAN, BASE.plusDays(1), 1);
        service.reserve(CarType.VAN, BASE.plusDays(1), 1); // would have failed if not expanded
    }

    @Test
    void overlapRejection() {
        LocalDateTime start = BASE.plusDays(2);
        service.reserve(CarType.VAN, start, 1);
        // try to book same van in overlapping slot (should fail)
        assertThrows(IllegalArgumentException.class, () ->
            service.reserve(CarType.VAN, start, 1)
        );
    }

    @Test
    void boundaryAllowed() {
        LocalDateTime start = BASE.plusDays(3);
        Reservation res1 = service.reserve(CarType.VAN, start, 1); // [d3, d4)
        // Book immediately after, which is allowed (boundary touch but not overlap)
        Reservation res2 = service.reserve(CarType.VAN, start.plusDays(1), 1); // [d4, d5)
        assertNotNull(res1);
        assertNotNull(res2);
        assertNotEquals(res1.getId(), res2.getId());
    }

    @Test
    void multiDayEndCalculation() {
        LocalDateTime start = BASE.plusDays(4);
        Reservation res = service.reserve(CarType.SEDAN, start, 3); // should end at start+3 days
        assertEquals(start, res.getStartDateTime());
        assertEquals(start.plusDays(3), res.getEndDateTime());
    }

    @Test
    void startDateMustNotBeInPast() {
        LocalDateTime past = BASE.minusDays(1);
        assertThrows(IllegalArgumentException.class, () ->
            service.reserve(CarType.SEDAN, past, 1)
        );
    }

    @Test
    void daysMustBePositive() {
        assertThrows(IllegalArgumentException.class, () ->
            service.reserve(CarType.SEDAN, BASE.plusDays(1), 0)
        );
    }
}
