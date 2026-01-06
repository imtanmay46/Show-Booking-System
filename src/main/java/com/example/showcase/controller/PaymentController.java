package com.example.showcase.controller;

import com.example.showcase.dto.request.CreatePaymentOrderRequest;
import com.example.showcase.dto.request.VerifyPaymentRequest;
import com.example.showcase.dto.response.MockPaymentResponse;
import com.example.showcase.dto.response.PaymentOrderResponse;
import com.example.showcase.dto.response.PaymentVerificationResponse;
import com.example.showcase.service.MockPaymentGatewayService;
import com.example.showcase.service.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final MockPaymentGatewayService mockPaymentGatewayService;

    public PaymentController(PaymentService paymentService, MockPaymentGatewayService mockPaymentGatewayService) {
        this.paymentService = paymentService;
        this.mockPaymentGatewayService = mockPaymentGatewayService;
    }

    @PostMapping("/create-order")
    public PaymentOrderResponse createPaymentOrder(@RequestBody CreatePaymentOrderRequest request) {
        return paymentService.createPaymentOrder(request);
    }

    @PostMapping("/verify")
    public PaymentVerificationResponse verifyPayment(@RequestBody VerifyPaymentRequest request) {
        return paymentService.verifyPayment(request);
    }

    // Mock payment simulation endpoint (for testing without frontend)
    @PostMapping("/mock-payment/{orderId}")
    public MockPaymentResponse simulateMockPayment(@PathVariable String orderId) {
        String paymentId = mockPaymentGatewayService.generateMockPaymentId();
        String signature = mockPaymentGatewayService.generateMockSignature();

        return new MockPaymentResponse(
                orderId,
                paymentId,
                signature,
                "Mock payment simulation completed. Use these values to verify payment."
        );
    }
}
