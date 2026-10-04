package com.ridelink.fare_payment_service.service;

import com.ridelink.fare_payment_service.entity.Payment;
import com.ridelink.fare_payment_service.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    void processPayment_shouldCreateSuccessfulPayment() {

        Payment savedPayment = new Payment();

        savedPayment.setRideId(1L);
        savedPayment.setAmount(new BigDecimal("1500.00"));
        savedPayment.setPaymentStatus("SUCCESS");
        savedPayment.setTransactionReference("TXN-test");
        savedPayment.setReceiptReference("RCP-test");

        when(paymentRepository.save(any(Payment.class)))
                .thenReturn(savedPayment);

        Payment result = paymentService.processPayment(
                1L,
                new BigDecimal("1500.00")
        );

        assertNotNull(result);
        assertEquals(1L, result.getRideId());
        assertEquals(
                new BigDecimal("1500.00"),
                result.getAmount()
        );
        assertEquals(
                "SUCCESS",
                result.getPaymentStatus()
        );

        assertNotNull(result.getTransactionReference());
        assertNotNull(result.getReceiptReference());

        verify(paymentRepository, times(1))
                .save(any(Payment.class));
    }

    @Test
    void getPaymentByRideId_shouldReturnPayment() {

        Payment payment = new Payment();

        payment.setRideId(1L);
        payment.setAmount(new BigDecimal("1500.00"));
        payment.setPaymentStatus("SUCCESS");

        when(
                paymentRepository
                        .findFirstByRideIdOrderByCreatedAtDesc(1L)
        ).thenReturn(Optional.of(payment));

        Optional<Payment> result =
                paymentService.getPaymentByRideId(1L);

        assertTrue(result.isPresent());

        assertEquals(
                1L,
                result.get().getRideId()
        );

        assertEquals(
                new BigDecimal("1500.00"),
                result.get().getAmount()
        );

        assertEquals(
                "SUCCESS",
                result.get().getPaymentStatus()
        );

        verify(
                paymentRepository,
                times(1)
        ).findFirstByRideIdOrderByCreatedAtDesc(1L);
    }

    @Test
    void getPaymentByRideId_shouldReturnEmptyWhenNotFound() {

        when(
                paymentRepository
                        .findFirstByRideIdOrderByCreatedAtDesc(99L)
        ).thenReturn(Optional.empty());

        Optional<Payment> result =
                paymentService.getPaymentByRideId(99L);

        assertTrue(result.isEmpty());

        verify(
                paymentRepository,
                times(1)
        ).findFirstByRideIdOrderByCreatedAtDesc(99L);
    }

    @Test
    void getPaymentByReceiptReference_shouldReturnPayment() {

        Payment payment = new Payment();

        payment.setRideId(1L);
        payment.setAmount(new BigDecimal("1000.00"));
        payment.setPaymentStatus("SUCCESS");
        payment.setReceiptReference("RCP-test");

        when(
                paymentRepository
                        .findByReceiptReference("RCP-test")
        ).thenReturn(Optional.of(payment));

        Optional<Payment> result =
                paymentService.getPaymentByReceiptReference("RCP-test");

        assertTrue(result.isPresent());

        assertEquals(
                "RCP-test",
                result.get().getReceiptReference()
        );

        assertEquals(
                1L,
                result.get().getRideId()
        );

        assertEquals(
                new BigDecimal("1000.00"),
                result.get().getAmount()
        );

        verify(
                paymentRepository,
                times(1)
        ).findByReceiptReference("RCP-test");
    }

    @Test
    void getPaymentByReceiptReference_shouldReturnEmptyWhenNotFound() {

        when(
                paymentRepository
                        .findByReceiptReference("RCP-not-found")
        ).thenReturn(Optional.empty());

        Optional<Payment> result =
                paymentService.getPaymentByReceiptReference(
                        "RCP-not-found"
                );

        assertTrue(result.isEmpty());

        verify(
                paymentRepository,
                times(1)
        ).findByReceiptReference("RCP-not-found");
    }
}