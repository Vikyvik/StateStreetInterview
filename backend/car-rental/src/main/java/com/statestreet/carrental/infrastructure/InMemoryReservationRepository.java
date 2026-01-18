package com.statestreet.carrental.infrastructure;

import com.statestreet.carrental.domain.Reservation;
import com.statestreet.carrental.domain.CarType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Stores reservations in memory.
 * Not thread-safe.
 */
public class InMemoryReservationRepository {
    private final List<Reservation> reservations = new ArrayList<>();

    public void save(Reservation r) {
        reservations.add(Objects.requireNonNull(r));
    }

    public List<Reservation> findAll() {
        return Collections.unmodifiableList(reservations);
    }

    public List<Reservation> findByType(CarType type) {
        return reservations.stream()
                .filter(r -> r.getType() == Objects.requireNonNull(type))
                .collect(Collectors.toUnmodifiableList());
    }
}
