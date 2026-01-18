package com.statestreet.carrental.infrastructure;

import com.statestreet.carrental.application.ports.CarInventory;
import com.statestreet.carrental.domain.Car;
import com.statestreet.carrental.domain.CarType;

import java.util.Collections;
import java.util.List;

/**
 * Returns a fixed set of cars per type, no persistence.
 */
public class InMemoryCarInventory implements CarInventory {
    private final List<Car> sedans;
    private final List<Car> suvs;
    private final List<Car> vans;

    public InMemoryCarInventory() {
        sedans = List.of(
                new Car("SEDAN-1", CarType.SEDAN),
                new Car("SEDAN-2", CarType.SEDAN)
        );
        suvs = List.of(
                new Car("SUV-1", CarType.SUV),
                new Car("SUV-2", CarType.SUV)
        );
        vans = List.of(
                new Car("VAN-1", CarType.VAN)
        );
    }

    @Override
    public List<Car> getCarsByType(CarType type) {
        if (type == CarType.SEDAN) return Collections.unmodifiableList(sedans);
        if (type == CarType.SUV) return Collections.unmodifiableList(suvs);
        if (type == CarType.VAN) return Collections.unmodifiableList(vans);
        return Collections.emptyList();
    }
}
