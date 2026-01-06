package com.example.showcase.dto.response;

public class ScreenResponse {

    private final Long id;
    private final String name;
    private final int totalSeats;
    private final Long theatreId;
    private final String theatreName;

    public ScreenResponse(Long id, String name, int totalSeats, Long theatreId, String theatreName){
        this.id = id;
        this.name = name;
        this.totalSeats = totalSeats;
        this.theatreId = theatreId;
        this.theatreName = theatreName;
    }

    public Long getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public int getTotalSeats(){
        return totalSeats;
    }

    public Long getTheatreId(){
        return theatreId;
    }

    public String getTheatreName(){
        return theatreName;
    }
}
