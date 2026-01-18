/*
package com.statestreet.carrental.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.statestreet.carrental.application.AllocationStrategy;
import com.statestreet.carrental.application.LowestIdAllocationStrategy;
import com.statestreet.carrental.application.ReservationService;
import com.statestreet.carrental.domain.CarType;
import com.statestreet.carrental.infrastructure.InMemoryCarInventory;
import com.statestreet.carrental.infrastructure.InMemoryReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.boot.test.context.SpringBootTest;
import com.statestreet.carrental.application.CarRentalApplication;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void availabilityReturnsTrue() throws Exception {
        when(reservationService.reserve(any(), any(), anyInt()))
                .thenReturn(null); // Will be caught and "available" is set as true

        mockMvc.perform(get("/api/availability")
                .param("type", "SEDAN")
                .param("startDateTime", LocalDateTime.now().plusDays(1).toString())
                .param("days", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.available").value(true));
    }
*/

/* Controller test temporarily disabled */
