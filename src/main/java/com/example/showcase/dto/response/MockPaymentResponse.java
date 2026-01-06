package com.example.showcase.dto.response;

public class MockPaymentResponse {
    private final String razorpayOrderId;
    private final String razorpayPaymentId;
    private final String razorpaySignature;
    private final String message;

    public MockPaymentResponse(String razorpayOrderId, String razorpayPaymentId, String razorpaySignature, String message) {
        this.razorpayOrderId = razorpayOrderId;
        this.razorpayPaymentId = razorpayPaymentId;
        this.razorpaySignature = razorpaySignature;
        this.message = message;
    }

    public String getRazorpayOrderId() { return razorpayOrderId; }
    public String getRazorpayPaymentId() { return razorpayPaymentId; }
    public String getRazorpaySignature() { return razorpaySignature; }
    public String getMessage() { return message; }
}
