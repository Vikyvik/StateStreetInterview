package com.statestreet.carrental.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public final class Reservation {
    private final String id;
    private final String carId;
    private final CarType type;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;

    public Reservation(String id, String carId, CarType type, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.carId = Objects.requireNonNull(carId, "carId must not be null");
        this.type = Objects.requireNonNull(type, "type must not be null");
        this.startDateTime = Objects.requireNonNull(startDateTime, "startDateTime must not be null");
        this.endDateTime = Objects.requireNonNull(endDateTime, "endDateTime must not be null");
    }

    public String getId() {
        return id;
    }

    public String getCarId() {
        return carId;
    }

    public CarType getType() {
        return type;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }
}
