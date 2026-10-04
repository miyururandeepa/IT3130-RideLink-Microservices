package com.ridelink.fare_payment_service.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class FareEstimateRequest {

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal distanceKm;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal durationMinutes;

    public FareEstimateRequest() {
    }

    public BigDecimal getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(BigDecimal distanceKm) {
        this.distanceKm = distanceKm;
    }

    public BigDecimal getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(BigDecimal durationMinutes) {
        this.durationMinutes = durationMinutes;
    }
}