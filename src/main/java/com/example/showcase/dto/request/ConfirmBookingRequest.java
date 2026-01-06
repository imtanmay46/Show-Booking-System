package com.example.showcase.dto.request;

public class ConfirmBookingRequest {
    private Long bookingId;
    private Long userId;

    public Long getBookingId(){ return bookingId; }
    public Long getUserId(){ return userId; }

    public void setBookingId(Long bookingId){ this.bookingId = bookingId; }
    public void setUserId(Long userId){ this.userId = userId; }
}