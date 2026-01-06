package com.example.showcase.dto.response;

import com.example.showcase.domain.enums.SeatType;
import com.example.showcase.domain.seat.Seat;

public class SeatResponse {

    private final Long id;
    private final String seatNumber;
    private final SeatType seatType;
    private final Long screenId;

    public SeatResponse(Long id, String seatNumber, SeatType seatType, Long screenId){
        this.id = id;
        this.seatNumber = seatNumber;
        this.seatType = seatType;
        this.screenId = screenId;
    }

    public Long getId(){
        return id;
    }

    public String getSeatNumber(){
        return seatNumber;
    }

    public SeatType getSeatType(){
        return seatType;
    }

    public Long getScreenId(){
        return screenId;
    }
}
