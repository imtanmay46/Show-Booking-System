package com.example.showcase.controller;

import com.example.showcase.domain.enums.BookingStatus;
import com.example.showcase.dto.request.ConfirmBookingRequest;
import com.example.showcase.dto.request.CreateBookingRequest;
import com.example.showcase.dto.response.AvailableSeatsResponse;
import com.example.showcase.dto.response.BookingResponse;
import com.example.showcase.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/shows/{showId}/seats")
    public AvailableSeatsResponse getAvailableSeats(@PathVariable Long showId) {
        return bookingService.getAvailableSeats(showId);
    }

    @PostMapping
    public BookingResponse createBooking(@RequestBody CreateBookingRequest request) {
        return bookingService.createBooking(request);
    }

    @PostMapping("/confirm")
    public BookingResponse confirmBooking(@RequestBody ConfirmBookingRequest request) {
        return bookingService.confirmBooking(request);
    }

    @GetMapping("/{bookingId}")
    public BookingResponse getBooking(@PathVariable Long bookingId) {
        return bookingService.getBooking(bookingId);
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> cancelBooking(
            @PathVariable Long bookingId,
            @RequestParam Long userId) {
        bookingService.cancelBooking(bookingId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public List<BookingResponse> getUserBookings(@PathVariable Long userId) {
        return bookingService.getUserBookings(userId);
    }

    @GetMapping("/user/{userId}/status/{status}")
    public List<BookingResponse> getUserBookingsByStatus(
            @PathVariable Long userId,
            @PathVariable BookingStatus status) {
        return bookingService.getUserBookingsByStatus(userId, status);
    }

    @GetMapping("/user/{userId}/upcoming")
    public List<BookingResponse> getUpcomingBookings(@PathVariable Long userId) {
        return bookingService.getUpcomingBookings(userId);
    }

    @GetMapping("/user/{userId}/past")
    public List<BookingResponse> getPastBookings(@PathVariable Long userId) {
        return bookingService.getPastBookings(userId);
    }
}