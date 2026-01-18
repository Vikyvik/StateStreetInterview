package com.statestreet.carrental.application;

import com.statestreet.carrental.domain.Car;
import com.statestreet.carrental.domain.Reservation;

import java.time.LocalDateTime;
import java.util.List;

public interface AllocationStrategy {
    /**
     * Allocates a car from the list that is available in the given time window.
     * @param cars available cars of right type and inventory
     * @param existingReservations already made reservations on these cars
     * @param start requested start of reservation
     * @param end requested end of reservation
     * @return Car to allocate, or null if none available
     */
    Car allocate(List<Car> cars, List<Reservation> existingReservations, LocalDateTime start, LocalDateTime end);
}
