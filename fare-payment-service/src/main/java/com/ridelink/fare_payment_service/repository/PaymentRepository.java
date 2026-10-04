package com.ridelink.fare_payment_service.repository;

import com.ridelink.fare_payment_service.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findFirstByRideIdOrderByCreatedAtDesc(Long rideId);

    Optional<Payment> findByTransactionReference(String transactionReference);

    Optional<Payment> findByReceiptReference(String receiptReference);
}
