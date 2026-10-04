package com.ridelink.fare_payment_service.service;

import com.ridelink.fare_payment_service.dto.FareEstimateRequest;
import com.ridelink.fare_payment_service.dto.FareEstimateResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class FareServiceTest {

    private final FareService fareService = new FareService();

    @Test
    void calculateEstimate_shouldCalculateCorrectFare() {

        FareEstimateRequest request = new FareEstimateRequest();
        request.setDistanceKm(new BigDecimal("10"));
        request.setDurationMinutes(new BigDecimal("20"));

        FareEstimateResponse result =
                fareService.calculateEstimate(request);

        assertNotNull(result);
        assertEquals(new BigDecimal("10"), result.getDistanceKm());
        assertEquals(new BigDecimal("20"), result.getDurationMinutes());
        assertEquals(new BigDecimal("1000.00"), result.getEstimatedFare());
    }

    @Test
    void calculateEstimate_shouldHandleDecimalValues() {

        FareEstimateRequest request = new FareEstimateRequest();
        request.setDistanceKm(new BigDecimal("5.5"));
        request.setDurationMinutes(new BigDecimal("10.5"));

        FareEstimateResponse result =
                fareService.calculateEstimate(request);

        assertEquals(new BigDecimal("592.50"), result.getEstimatedFare());
    }

    @Test
    void calculateEstimate_shouldReturnTwoDecimalPlaces() {

        FareEstimateRequest request = new FareEstimateRequest();
        request.setDistanceKm(new BigDecimal("1.25"));
        request.setDurationMinutes(new BigDecimal("3.33"));

        FareEstimateResponse result =
                fareService.calculateEstimate(request);

        assertEquals(2, result.getEstimatedFare().scale());
    }

    @Test
    void calculateFinalFare_shouldCalculateCorrectFare() {

        BigDecimal finalFare =
                fareService.calculateFinalFare(
                        new BigDecimal("10"),
                        new BigDecimal("20")
                );

        assertEquals(new BigDecimal("1000.00"), finalFare);
    }
}