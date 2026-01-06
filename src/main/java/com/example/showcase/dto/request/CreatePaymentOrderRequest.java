package com.example.showcase.dto.request;

public class CreatePaymentOrderRequest {
    private Long bookingId;

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }
}
