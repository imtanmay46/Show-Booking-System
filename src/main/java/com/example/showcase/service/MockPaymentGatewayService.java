package com.example.showcase.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class MockPaymentGatewayService {

    public Map<String, Object> createOrder(int amount, String currency) {
        Map<String, Object> order = new HashMap<>();

        String orderId = "order_" + UUID.randomUUID().toString().substring(0, 8);

        order.put("id", orderId);
        order.put("amount", amount);
        order.put("currency", currency);
        order.put("status", "created");
        order.put("receipt", "txn_" + System.currentTimeMillis());

        System.out.println(" MOCK: Created payment order: " + orderId + " for amount: " + amount);

        return order;
    }

    public boolean verifyPaymentSignature(String orderId, String paymentId, String signature) {

        System.out.println("   MOCK: Verifying payment...");
        System.out.println("   Order ID: " + orderId);
        System.out.println("   Payment ID: " + paymentId);
        System.out.println("   Signature: " + signature);

        boolean isValid = Math.random() < 0.95;

        if (isValid) {
            System.out.println("MOCK: Payment verification SUCCESSFUL");
        } else {
            System.out.println("MOCK: Payment verification FAILED");
        }

        return isValid;
    }

    public String generateMockPaymentId() {
        return "pay_" + UUID.randomUUID().toString().substring(0, 8);
    }

    public String generateMockSignature() {
        return "sig_" + UUID.randomUUID().toString();
    }
}
