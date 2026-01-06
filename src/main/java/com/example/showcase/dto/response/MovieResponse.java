package com.example.showcase.dto.response;

public class MovieResponse {

    private final Long id;
    private final String title;
    private final String language;
    private final int durationInMinutes;

    public MovieResponse(Long id, String title, String language, int durationInMinutes){
        this.id = id;
        this.title = title;
        this.language = language;
        this.durationInMinutes = durationInMinutes;
    }

    public Long getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getLanguage(){
        return language;
    }

    public int getDurationInMinutes(){
        return durationInMinutes;
    }
}
