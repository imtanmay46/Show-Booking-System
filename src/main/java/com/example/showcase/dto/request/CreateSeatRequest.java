package com.example.showcase.dto.request;

import com.example.showcase.domain.enums.SeatType;

public class CreateSeatRequest {

    private String seatNumber;
    private SeatType seatType;
    private Long screenId;

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
