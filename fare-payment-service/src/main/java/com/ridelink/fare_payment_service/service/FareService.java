package com.ridelink.fare_payment_service.service;

import com.ridelink.fare_payment_service.dto.FareEstimateRequest;
import com.ridelink.fare_payment_service.dto.FareEstimateResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class FareService {

    private static final BigDecimal BASE_FARE = new BigDecimal("100.00");
    private static final BigDecimal PER_KM_RATE = new BigDecimal("80.00");
    private static final BigDecimal PER_MINUTE_RATE = new BigDecimal("5.00");

    public FareEstimateResponse calculateEstimate(FareEstimateRequest request) {

        BigDecimal distanceCharge =
                request.getDistanceKm().multiply(PER_KM_RATE);

        BigDecimal durationCharge =
                request.getDurationMinutes().multiply(PER_MINUTE_RATE);

        BigDecimal estimatedFare =
                BASE_FARE
                        .add(distanceCharge)
                        .add(durationCharge)
                        .setScale(2, RoundingMode.HALF_UP);

        return new FareEstimateResponse(
                request.getDistanceKm(),
                request.getDurationMinutes(),
                estimatedFare
        );
    }

    public BigDecimal calculateFinalFare(
            BigDecimal distanceKm,
            BigDecimal durationMinutes) {

        BigDecimal distanceCharge =
                distanceKm.multiply(PER_KM_RATE);

        BigDecimal durationCharge =
                durationMinutes.multiply(PER_MINUTE_RATE);

        return BASE_FARE
                .add(distanceCharge)
                .add(durationCharge)
                .setScale(2, RoundingMode.HALF_UP);
    }
}