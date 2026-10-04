package com.ridelink.fare_payment_service.dto;

import java.math.BigDecimal;

public class FareEstimateResponse {

    private BigDecimal distanceKm;
    private BigDecimal durationMinutes;
    private BigDecimal estimatedFare;

    public FareEstimateResponse() {
    }

    public FareEstimateResponse(
            BigDecimal distanceKm,
            BigDecimal durationMinutes,
            BigDecimal estimatedFare) {

        this.distanceKm = distanceKm;
        this.durationMinutes = durationMinutes;
        this.estimatedFare = estimatedFare;
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

    public BigDecimal getEstimatedFare() {
        return estimatedFare;
    }

    public void setEstimatedFare(BigDecimal estimatedFare) {
        this.estimatedFare = estimatedFare;
    }
}