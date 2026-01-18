package com.statestreet.carrental.application.ports;

import com.statestreet.carrental.domain.CarType;
import com.statestreet.carrental.domain.Reservation;

import java.util.List;

public interface ReservationRepository {
    void save(Reservation r);
    List<Reservation> findAll();
    List<Reservation> findByType(CarType type);
}
