package com.statestreet.carrental.application;

import com.statestreet.carrental.domain.Car;
import com.statestreet.carrental.domain.Reservation;
import com.statestreet.carrental.domain.TimeWindow;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public class LowestIdAllocationStrategy implements AllocationStrategy {
    @Override
    public Car allocate(List<Car> cars, List<Reservation> reservations,
                        LocalDateTime start, LocalDateTime end) {
        return cars.stream()
                .sorted(Comparator.comparing(Car::getId))
                .filter(car -> reservations.stream()
                        .filter(r -> r.getCarId().equals(car.getId()))
                        .noneMatch(r -> TimeWindow.overlaps(
                                r.getStartDateTime(), r.getEndDateTime(), start, end
                        ))
                )
                .findFirst()
                .orElse(null);
    }
}
