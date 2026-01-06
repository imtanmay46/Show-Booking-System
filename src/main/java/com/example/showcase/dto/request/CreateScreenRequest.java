package com.example.showcase.dto.request;

public class CreateScreenRequest {

    private String name;
    private int totalSeats;
    private Long theatreId;

    protected CreateScreenRequest(){}

    public String getName(){
        return name;
    }

    public int getTotalSeats(){
        return totalSeats;
    }

    public Long getTheatreId(){
        return theatreId;
    }
}
