package com.example.showcase.dto.response;

import java.time.LocalDateTime;

public class ShowResponse {

    private final Long id;
    private final Long movieId;
    private final Long screenId;
    private final LocalDateTime startTime;
    private final int price;

    public ShowResponse(Long id, Long movieId, Long screenId, LocalDateTime startTime, int price){
        this.id = id;
        this.movieId = movieId;
        this.screenId = screenId;
        this.startTime = startTime;
        this.price = price;
    }

    public Long getId(){
        return id;
    }

    public Long getMovieId(){
        return movieId;
    }

    public Long getScreenId(){
        return screenId;
    }

    public LocalDateTime getStartTime(){
        return startTime;
    }

    public int getPrice(){
        return price;
    }
}