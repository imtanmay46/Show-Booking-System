package com.example.showcase.domain.location;

import com.example.showcase.domain.common.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "screens")
public class Screen extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int totalSeats;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "theatre_id")
    private Theatre theatre;

    protected Screen(){}

    public Screen(String name, int totalSeats, Theatre theatre){
        this.name = name;
        this.totalSeats = totalSeats;
        this.theatre = theatre;
    }

    public String getName(){
        return name;
    }

    public int getTotalSeats(){
        return totalSeats;
    }

    public Theatre getTheatre(){
        return theatre;
    }
}
