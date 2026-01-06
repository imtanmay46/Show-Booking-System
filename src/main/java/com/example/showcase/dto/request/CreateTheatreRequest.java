package com.example.showcase.dto.request;

public class CreateTheatreRequest {

    private String name;
    private Long cityId;

    protected CreateTheatreRequest(){}

    public String getName(){
        return name;
    }

    public Long getCityId(){
        return cityId;
    }
}
