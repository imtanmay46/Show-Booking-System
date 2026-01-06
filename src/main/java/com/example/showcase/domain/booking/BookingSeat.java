package com.example.showcase.domain.booking;

import com.example.showcase.domain.common.BaseEntity;
import com.example.showcase.domain.seat.Seat;
import jakarta.persistence.*;

@Entity
@Table(name = "booking_seats")
public class BookingSeat extends BaseEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @Column(nullable = false)
    private int price;

    protected BookingSeat() {}

    public BookingSeat(Booking booking, Seat seat, int price) {
        this.booking = booking;
        this.seat = seat;
        this.price = price;
    }

    public Booking getBooking() { return booking; }
    public Seat getSeat() { return seat; }
    public int getPrice() { return price; }
}