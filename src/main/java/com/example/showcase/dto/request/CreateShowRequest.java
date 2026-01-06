package com.example.showcase.dto.request;

import java.time.LocalDateTime;

public class CreateShowRequest {

    private Long movieId;
    private Long screenId;
    private LocalDateTime startTime;
    private int price;

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
