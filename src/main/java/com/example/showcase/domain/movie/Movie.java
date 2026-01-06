package com.example.showcase.domain.movie;

import com.example.showcase.domain.common.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "movies")
public class Movie extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private int durationInMinutes;

    @Column(nullable = false)
    private String language;

    protected Movie(){}

    public Movie(String title, int durationInMinutes, String language){
        this.title = title;
        this.durationInMinutes = durationInMinutes;
        this.language = language;
    }

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
