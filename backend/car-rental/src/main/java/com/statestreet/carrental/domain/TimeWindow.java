package com.statestreet.carrental.domain;

import java.time.LocalDateTime;

public class TimeWindow {
  
    public static boolean overlaps(LocalDateTime aStart, LocalDateTime aEnd,
                                   LocalDateTime bStart, LocalDateTime bEnd) {
        if (aStart.equals(bStart)) return true;
        if (aEnd.equals(bStart) || bEnd.equals(aStart)) return false;
        return bStart.isBefore(aEnd) && bEnd.isAfter(aStart);
    }
}
