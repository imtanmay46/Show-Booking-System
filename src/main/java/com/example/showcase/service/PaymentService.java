package com.example.showcase.service;

import com.example.showcase.domain.booking.Booking;
import com.example.showcase.domain.booking.Payment;
import com.example.showcase.domain.enums.PaymentStatus;
import com.example.showcase.dto.request.CreatePaymentOrderRequest;
import com.example.showcase.dto.request.VerifyPaymentRequest;
import com.example.showcase.dto.response.PaymentOrderResponse;
import com.example.showcase.dto.response.PaymentVerificationResponse;
import com.example.showcase.repository.BookingRepository;
import com.example.showcase.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final MockPaymentGatewayService mockPaymentGatewayService;

    @Value("${payment.gateway.currency:INR}")
    private String currency;

    public PaymentService(
            PaymentRepository paymentRepository,
            BookingRepository bookingRepository,
            MockPaymentGatewayService mockPaymentGatewayService) {
        this.paymentRepository = paymentRepository;
        this.bookingRepository = bookingRepository;
        this.mockPaymentGatewayService = mockPaymentGatewayService;
    }

    @Transactional
    public PaymentOrderResponse createPaymentOrder(CreatePaymentOrderRequest request) {
        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (booking.getBookingStatus().name().equals("CONFIRMED")) {
            throw new RuntimeException("Booking is already confirmed");
        }

        if (booking.getBookingStatus().name().equals("CANCELLED")) {
            throw new RuntimeException("Booking is cancelled");
        }

        if (booking.getPayment() != null && booking.getPayment().getStatus() == PaymentStatus.SUCCESS) {
            throw new RuntimeException("Payment already completed for this booking");
        }

        Map<String, Object> mockOrder = mockPaymentGatewayService.createOrder(
                booking.getTotalAmount(),
                currency
        );

        String orderId = (String) mockOrder.get("id");

        Payment payment = new Payment(
                booking,
                orderId,
                booking.getTotalAmount(),
                currency
        );

        booking.setPayment(payment);
        paymentRepository.save(payment);

        return new PaymentOrderResponse(
                orderId,
                booking.getTotalAmount(),
                currency,
                booking.getId(),
                "mock_key_test_123"
        );
    }

    @Transactional
    public PaymentVerificationResponse verifyPayment(VerifyPaymentRequest request) {
        Payment payment = paymentRepository.findByRazorpayOrderId(request.getRazorpayOrderId())
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        boolean isValid = mockPaymentGatewayService.verifyPaymentSignature(
                request.getRazorpayOrderId(),
                request.getRazorpayPaymentId(),
                request.getRazorpaySignature()
        );

        if (isValid) {
            payment.markSuccess(request.getRazorpayPaymentId(), request.getRazorpaySignature());
            paymentRepository.save(payment);

            Booking booking = payment.getBooking();
            booking.confirm();
            bookingRepository.save(booking);

            return new PaymentVerificationResponse(
                    true,
                    "Payment verified successfully",
                    booking.getId()
            );
        } else {
            payment.markFailed();
            paymentRepository.save(payment);

            return new PaymentVerificationResponse(
                    false,
                    "Payment verification failed",
                    payment.getBooking().getId()
            );
        }
    }
}
