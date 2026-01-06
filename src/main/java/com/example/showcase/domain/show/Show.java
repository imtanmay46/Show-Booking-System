package com.example.showcase.domain.show;

import com.example.showcase.domain.common.BaseEntity;
import com.example.showcase.domain.location.Screen;
import com.example.showcase.domain.movie.Movie;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "shows")
public class Show extends BaseEntity {

    @ManyToOne(optional = false)
    private Movie movie;

    @ManyToOne(optional = false)
    private Screen screen;

    @Column(nullable = false)
    private LocalDateTime startTime;
    private int price;

    protected Show(){}

    public Show(Movie movie, Screen screen, LocalDateTime startTime, int price){
        this.movie = movie;
        this.screen = screen;
        this.startTime = startTime;
        this.price = price;
    }

    public Movie getMovie(){
        return movie;
    }

    public Screen getScreen(){
        return screen;
    }

    public LocalDateTime getStartTime(){
        return startTime;
    }

    public int getPrice(){
        return price;
    }
}
