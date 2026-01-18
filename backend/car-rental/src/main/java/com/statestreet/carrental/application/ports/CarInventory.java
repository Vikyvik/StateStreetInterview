package com.statestreet.carrental.application.ports;

import com.statestreet.carrental.domain.Car;
import com.statestreet.carrental.domain.CarType;

import java.util.List;

public interface CarInventory {
    List<Car> getCarsByType(CarType type);
}
