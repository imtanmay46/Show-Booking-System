package com.example.showcase.domain.seat;

import com.example.showcase.domain.common.BaseEntity;
import com.example.showcase.domain.enums.SeatType;
import com.example.showcase.domain.location.Screen;
import jakarta.persistence.*;

@Entity
@Table(name = "seats")
public class Seat extends BaseEntity {

    @Column(nullable = false)
    private String seatNumber;

    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    @ManyToOne(optional = false)
    private Screen screen;

    protected Seat(){}

    public Seat(String seatNumber, SeatType seatType, Screen screen){
        this.seatNumber = seatNumber;
        this.seatType = seatType;
        this.screen = screen;
    }

    public String getSeatNumber(){
        return seatNumber;
    }

    public SeatType getSeatType(){
        return seatType;
    }

    public Screen getScreen(){
        return screen;
    }
}
