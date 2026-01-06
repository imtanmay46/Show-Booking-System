package com.example.showcase.domain.booking;

import com.example.showcase.domain.common.BaseEntity;
import com.example.showcase.domain.enums.BookingStatus;
import com.example.showcase.domain.show.Show;
import com.example.showcase.domain.user.User;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bookings")
public class Booking extends BaseEntity {

    @ManyToOne(optional = false)
    private User user;

    @ManyToOne(optional = false)
    private Show show;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<BookingSeat> bookingSeats = new ArrayList<>();

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    private Payment payment;

    @Column(nullable = false)
    private int totalAmount;

    protected Booking(){}

    public Booking(User user, Show show){
        this.user = user;
        this.show = show;
        this.bookingStatus = BookingStatus.CREATED;
        this.totalAmount = 0;
    }

    public void addBookingSeat(BookingSeat bookingSeat) {
        this.bookingSeats.add(bookingSeat);
        this.totalAmount += bookingSeat.getPrice();
    }

    public void confirm(){
        if (this.bookingStatus != BookingStatus.CREATED) {
            throw new IllegalStateException("Only CREATED bookings can be confirmed");
        }
        this.bookingStatus = BookingStatus.CONFIRMED;
    }

    public void cancel(){
        if (this.bookingStatus == BookingStatus.CANCELLED) {
            throw new IllegalStateException("Booking is already cancelled");
        }
        this.bookingStatus = BookingStatus.CANCELLED;
    }

    public User getUser(){ return user; }
    public Show getShow(){ return show; }
    public BookingStatus getBookingStatus(){ return bookingStatus; }
    public List<BookingSeat> getBookingSeats(){ return bookingSeats; }
    public int getTotalAmount(){ return totalAmount; }
    public Payment getPayment() { return payment; }

    public void setPayment(Payment payment) { this.payment = payment; }
}