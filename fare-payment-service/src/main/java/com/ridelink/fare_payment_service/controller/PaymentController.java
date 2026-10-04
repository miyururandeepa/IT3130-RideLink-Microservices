package com.ridelink.fare_payment_service.controller;

import com.ridelink.fare_payment_service.dto.PaymentRequest;
import com.ridelink.fare_payment_service.entity.Payment;
import com.ridelink.fare_payment_service.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Operation(
            summary = "Process simulated payment",
            description = "Records a simulated successful payment for a ride and generates transaction and receipt references."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Payment processed successfully"
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid ride ID or payment amount"
    )
    @PostMapping
    public ResponseEntity<Payment> processPayment(
            @Valid @RequestBody PaymentRequest request) {

        Payment payment = paymentService.processPayment(
                request.getRideId(),
                request.getAmount()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(payment);
    }

    @Operation(
            summary = "Get payment by ride ID",
            description = "Retrieves the payment record associated with a ride."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Payment found"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Payment not found for the specified ride"
    )
    @GetMapping("/{rideId}")
    public ResponseEntity<Payment> getPaymentByRideId(
            @PathVariable Long rideId) {

        return paymentService.getPaymentByRideId(rideId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Retrieve receipt by reference",
            description = "Retrieves the payment receipt using its receipt reference."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Receipt found"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Receipt not found"
    )
    @GetMapping("/receipt/{receiptReference}")
    public ResponseEntity<Payment> getReceipt(
            @PathVariable String receiptReference) {

        return paymentService.getPaymentByReceiptReference(receiptReference)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}