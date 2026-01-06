package com.example.showcase.dto.response;

import com.example.showcase.domain.enums.SeatStatus;
import com.example.showcase.domain.enums.SeatType;
import java.util.List;

public class AvailableSeatsResponse {
    private final Long showId;
    private final List<SeatAvailability> seats;

    public AvailableSeatsResponse(Long showId, List<SeatAvailability> seats) {
        this.showId = showId;
        this.seats = seats;
    }

    public Long getShowId(){ return showId; }
    public List<SeatAvailability> getSeats(){ return seats; }

    public static class SeatAvailability {
        private final Long seatId;
        private final String seatNumber;
        private final SeatType seatType;
        private final SeatStatus status;

        public SeatAvailability(Long seatId, String seatNumber, SeatType seatType, SeatStatus status) {
            this.seatId = seatId;
            this.seatNumber = seatNumber;
            this.seatType = seatType;
            this.status = status;
        }

        public Long getSeatId(){ return seatId; }
        public String getSeatNumber(){ return seatNumber; }
        public SeatType getSeatType(){ return seatType; }
        public SeatStatus getStatus(){ return status; }
    }
}