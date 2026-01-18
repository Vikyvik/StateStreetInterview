package com.statestreet.carrental.infrastructure;

import com.statestreet.carrental.domain.CarType;
import com.statestreet.carrental.domain.Reservation;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryReservationRepositoryTest {

    private Reservation res(String id, String carId, CarType type) {
        LocalDateTime now = LocalDateTime.now();
        return new Reservation(id, carId, type, now, now.plusHours(2));
    }

    @Test
    void saveAndFindAll() {
        InMemoryReservationRepository repo = new InMemoryReservationRepository();
        Reservation r1 = res("1", "A", CarType.SEDAN);
        Reservation r2 = res("2", "B", CarType.SUV);
        repo.save(r1);
        repo.save(r2);
        List<Reservation> all = repo.findAll();
        assertEquals(2, all.size());
        assertTrue(all.contains(r1));
        assertTrue(all.contains(r2));
    }

    @Test
    void findByTypeOnlyReturnsMatching() {
        InMemoryReservationRepository repo = new InMemoryReservationRepository();
        Reservation r1 = res("1", "A", CarType.SEDAN);
        Reservation r2 = res("2", "B", CarType.SUV);
        Reservation r3 = res("3", "C", CarType.SEDAN);
        repo.save(r1);
        repo.save(r2);
        repo.save(r3);

        List<Reservation> sedans = repo.findByType(CarType.SEDAN);
        assertEquals(2, sedans.size());
        assertTrue(sedans.contains(r1));
        assertTrue(sedans.contains(r3));
        assertFalse(sedans.contains(r2));

        List<Reservation> suvs = repo.findByType(CarType.SUV);
        assertEquals(1, suvs.size());
        assertTrue(suvs.contains(r2));
    }

    @Test
    void findAllReturnsUnmodifiableList() {
        InMemoryReservationRepository repo = new InMemoryReservationRepository();
        repo.save(res("1", "A", CarType.SUV));
        List<Reservation> all = repo.findAll();
        assertThrows(UnsupportedOperationException.class, () -> all.add(res("2", "B", CarType.SEDAN)));
    }
}
