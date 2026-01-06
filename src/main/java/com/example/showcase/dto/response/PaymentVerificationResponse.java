package com.example.showcase.dto.response;

public class PaymentVerificationResponse {
    private final boolean verified;
    private final String message;
    private final Long bookingId;

    public PaymentVerificationResponse(boolean verified, String message, Long bookingId) {
        this.verified = verified;
        this.message = message;
        this.bookingId = bookingId;
    }

    public boolean isVerified() { return verified; }
    public String getMessage() { return message; }
    public Long getBookingId() { return bookingId; }
}
