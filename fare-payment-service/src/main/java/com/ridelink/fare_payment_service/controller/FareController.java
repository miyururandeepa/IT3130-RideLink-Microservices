package com.ridelink.fare_payment_service.controller;

import com.ridelink.fare_payment_service.dto.FareEstimateRequest;
import com.ridelink.fare_payment_service.dto.FareEstimateResponse;
import com.ridelink.fare_payment_service.service.FareService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/fares")
public class FareController {

    private final FareService fareService;

    public FareController(FareService fareService) {
        this.fareService = fareService;
    }

    @Operation(
            summary = "Estimate ride fare",
            description = "Calculates an estimated fare using distance and duration."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Fare estimate calculated successfully"
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid distance or duration"
    )
    @PostMapping("/estimate")
    public ResponseEntity<FareEstimateResponse> estimateFare(
            @Valid @RequestBody FareEstimateRequest request) {

        FareEstimateResponse response =
                fareService.calculateEstimate(request);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Calculate final ride fare",
            description = "Calculates the final fare using distance and duration."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Final fare calculated successfully"
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid distance or duration"
    )
    @PostMapping("/final")
    public ResponseEntity<BigDecimal> calculateFinalFare(
            @Valid @RequestBody FareEstimateRequest request) {

        BigDecimal finalFare =
                fareService.calculateFinalFare(
                        request.getDistanceKm(),
                        request.getDurationMinutes()
                );

        return ResponseEntity.ok(finalFare);
    }
}