package com.statestreet.carrental.domain;

import java.util.Objects;

public final class Car {
    private final String id;
    private final CarType type;

    public Car(String id, CarType type) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.type = Objects.requireNonNull(type, "type must not be null");
    }

    public String getId() {
        return id;
    }

    public CarType getType() {
        return type;
    }
}
