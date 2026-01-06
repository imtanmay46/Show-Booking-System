package com.example.showcase.dto.response;

public class TheatreResponse {

    private final Long id;
    private final String name;
    private final Long cityId;
    private final String cityName;

    public TheatreResponse(Long id, String name, Long cityId, String cityName){
        this.id = id;
        this.name = name;
        this.cityId = cityId;
        this.cityName = cityName;
    }

    public Long getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public Long getCityId(){
        return cityId;
    }

    public String getCityName(){
        return cityName;
    }
}
