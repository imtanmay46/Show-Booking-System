package com.example.showcase.dto.request;

public class CreateMovieRequest {

    private String title;
    private int durationInMinutes;
    private String language;

    public String getTitle(){
        return title;
    }

    public int getDurationInMinutes(){
        return durationInMinutes;
    }

    public String getLanguage(){
        return language;
    }
}
