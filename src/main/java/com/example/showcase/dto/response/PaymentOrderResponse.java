package com.example.showcase.dto.response;

public class PaymentOrderResponse {
    private final String razorpayOrderId;
    private final int amount;
    private final String currency;
    private final Long bookingId;
    private final String razorpayKeyId;

    public PaymentOrderResponse(String razorpayOrderId, int amount, String currency, Long bookingId, String razorpayKeyId) {
        this.razorpayOrderId = razorpayOrderId;
        this.amount = amount;
        this.currency = currency;
        this.bookingId = bookingId;
        this.razorpayKeyId = razorpayKeyId;
    }

    public String getRazorpayOrderId() { return razorpayOrderId; }
    public int getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public Long getBookingId() { return bookingId; }
    public String getRazorpayKeyId() { return razorpayKeyId; }
}
