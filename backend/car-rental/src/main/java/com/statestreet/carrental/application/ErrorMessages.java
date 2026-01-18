package com.statestreet.carrental.application;

// Centralizes all error messages for re-use and maintainability
final class ErrorMessages {
    static final String ERR_DAYS = "days must be > 0";
    static final String ERR_PAST = "start may not be in the past";
    static final String ERR_NO_CAR = "No available car for requested time window";

    private ErrorMessages() {} // not instantiable
}
