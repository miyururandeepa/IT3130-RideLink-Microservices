package com.ridelink.fare_payment_service.service;

import com.ridelink.fare_payment_service.entity.Payment;
import com.ridelink.fare_payment_service.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment processPayment(Long rideId, BigDecimal amount) {

        Payment payment = new Payment();

        payment.setRideId(rideId);
        payment.setAmount(amount);
        payment.setPaymentStatus("SUCCESS");
        payment.setTransactionReference("TXN-" + UUID.randomUUID());
        payment.setReceiptReference("RCP-" + UUID.randomUUID());
        payment.setCreatedAt(LocalDateTime.now());

        return paymentRepository.save(payment);
    }

    public Optional<Payment> getPaymentByRideId(Long rideId) {
        return paymentRepository.findFirstByRideIdOrderByCreatedAtDesc(rideId);
    }

    public Optional<String> getPaymentStatusByRideId(Long rideId) {
        return paymentRepository.findFirstByRideIdOrderByCreatedAtDesc(rideId)
                .map(Payment::getPaymentStatus);
    }

    public Optional<Payment> getPaymentByReceiptReference(String receiptReference) {
        return paymentRepository.findByReceiptReference(receiptReference);
    }
}