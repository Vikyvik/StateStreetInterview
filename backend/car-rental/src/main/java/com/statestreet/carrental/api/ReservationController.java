package com.statestreet.carrental.api;

import com.statestreet.carrental.application.ReservationService;
import com.statestreet.carrental.domain.CarType;
import com.statestreet.carrental.domain.Reservation;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/availability")
    public Map<String, Boolean> availability(
            @RequestParam CarType type,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime,
            @RequestParam int days
    ) {
        boolean available;
        try {
            reservationService.reserve(type, startDateTime, days);
            available = true;
        } catch (Exception e) {
            available = false;
        }
        return Map.of("available", available);
    }

    @PostMapping("/reservations")
    public ResponseEntity<?> reserve(@RequestBody ReservationRequest request) {
        try {
            Reservation res = reservationService.reserve(
                    request.type,
                    request.startDateTime,
                    request.days
            );
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    public static class ReservationRequest {
        public CarType type;
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        public LocalDateTime startDateTime;
        public int days;
    }
}
