package com.statestreet.carrental.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

import com.statestreet.carrental.domain.TimeWindow;

class TimeWindowTest {
    @Test
    void overlapDetected() {
        // 11–13 vs 10–12 (should overlap: 11–12)
        LocalDateTime aStart = LocalDateTime.of(2023, 1, 1, 11, 0);
        LocalDateTime aEnd = LocalDateTime.of(2023, 1, 1, 13, 0);
        LocalDateTime bStart = LocalDateTime.of(2023, 1, 1, 10, 0);
        LocalDateTime bEnd = LocalDateTime.of(2023, 1, 1, 12, 0);
        assertTrue(TimeWindow.overlaps(aStart, aEnd, bStart, bEnd));
    }

    @Test
    void boundaryAllowed() {
        // 12–14 vs 10–12 (should NOT overlap, boundary touch allowed)
        LocalDateTime aStart = LocalDateTime.of(2023, 1, 1, 12, 0);
        LocalDateTime aEnd = LocalDateTime.of(2023, 1, 1, 14, 0);
        LocalDateTime bStart = LocalDateTime.of(2023, 1, 1, 10, 0);
        LocalDateTime bEnd = LocalDateTime.of(2023, 1, 1, 12, 0);
        assertFalse(TimeWindow.overlaps(aStart, aEnd, bStart, bEnd));
    }

    @Test
    void sameStartRejected() {
        // exact same start should be considered overlapping
        LocalDateTime aStart = LocalDateTime.of(2023, 1, 1, 12, 0);
        LocalDateTime aEnd = LocalDateTime.of(2023, 1, 1, 14, 0);
        LocalDateTime bStart = LocalDateTime.of(2023, 1, 1, 12, 0);
        LocalDateTime bEnd = LocalDateTime.of(2023, 1, 1, 16, 0);
        assertTrue(TimeWindow.overlaps(aStart, aEnd, bStart, bEnd));
    }
}
