package com.example.showcase.dto.response;

import com.example.showcase.domain.enums.BookingStatus;
import java.util.List;

public class BookingResponse {

    private final Long bookingId;
    private final Long userId;
    private final Long showId;
    private final BookingStatus bookingStatus;
    private final List<SeatInfo> seats;
    private final int totalAmount;

    public BookingResponse(Long bookingId, Long userId, Long showId, BookingStatus bookingStatus, List<SeatInfo> seats, int totalAmount){
        this.bookingId = bookingId;
        this.userId = userId;
        this.showId = showId;
        this.bookingStatus = bookingStatus;
        this.seats = seats;
        this.totalAmount = totalAmount;
    }

    // Getters
    public Long getBookingId(){ return bookingId; }
    public Long getUserId(){ return userId; }
    public Long getShowId(){ return showId; }
    public BookingStatus getBookingStatus(){ return bookingStatus; }
    public List<SeatInfo> getSeats(){ return seats; }
    public int getTotalAmount(){ return totalAmount; }

    public static class SeatInfo {
        private final Long seatId;
        private final String seatNumber;
        private final int price;

        public SeatInfo(Long seatId, String seatNumber, int price) {
            this.seatId = seatId;
            this.seatNumber = seatNumber;
            this.price = price;
        }

        public Long getSeatId(){ return seatId; }
        public String getSeatNumber(){ return seatNumber; }
        public int getPrice(){ return price; }
    }
}