package com.statestreet.carrental.infrastructure;

import com.statestreet.carrental.domain.CarType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryCarInventoryTest {

    @Test
    void getCarsByTypeReturnsExpectedCounts() {
        InMemoryCarInventory inventory = new InMemoryCarInventory();
        assertEquals(2, inventory.getCarsByType(CarType.SEDAN).size(), "Should return 2 sedans");
        assertEquals(2, inventory.getCarsByType(CarType.SUV).size(), "Should return 2 suvs");
        assertEquals(1, inventory.getCarsByType(CarType.VAN).size(), "Should return 1 van");
    }
}
