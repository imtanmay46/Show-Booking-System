package com.example.showcase.service;

import com.example.showcase.domain.booking.Booking;
import com.example.showcase.domain.booking.BookingSeat;
import com.example.showcase.domain.enums.BookingStatus;
import com.example.showcase.domain.enums.SeatStatus;
import com.example.showcase.domain.seat.Seat;
import com.example.showcase.domain.show.Show;
import com.example.showcase.domain.show.ShowSeat;
import com.example.showcase.domain.user.User;
import com.example.showcase.dto.request.ConfirmBookingRequest;
import com.example.showcase.dto.request.CreateBookingRequest;
import com.example.showcase.dto.response.AvailableSeatsResponse;
import com.example.showcase.dto.response.BookingResponse;
import com.example.showcase.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private static final int LOCK_DURATION_MINUTES = 10;

    private final BookingRepository bookingRepository;
    private final BookingSeatRepository bookingSeatRepository;
    private final UserRepository userRepository;
    private final ShowRepository showRepository;
    private final ShowSeatRepository showSeatRepository;
    private final SeatRepository seatRepository;

    public BookingService(
            BookingRepository bookingRepository,
            BookingSeatRepository bookingSeatRepository,
            UserRepository userRepository,
            ShowRepository showRepository,
            ShowSeatRepository showSeatRepository,
            SeatRepository seatRepository) {
        this.bookingRepository = bookingRepository;
        this.bookingSeatRepository = bookingSeatRepository;
        this.userRepository = userRepository;
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
        this.seatRepository = seatRepository;
    }

    @Transactional(readOnly = true)
    public AvailableSeatsResponse getAvailableSeats(Long showId) {
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new RuntimeException("Show not found"));

        List<ShowSeat> showSeats = showSeatRepository.findByShowId(showId);

        releaseExpiredLocks(showSeats);

        List<AvailableSeatsResponse.SeatAvailability> seatAvailabilities = showSeats.stream()
                .map(ss -> new AvailableSeatsResponse.SeatAvailability(
                        ss.getSeat().getId(),
                        ss.getSeat().getSeatNumber(),
                        ss.getSeat().getSeatType(),
                        ss.getStatus()
                ))
                .collect(Collectors.toList());

        return new AvailableSeatsResponse(showId, seatAvailabilities);
    }

    @Transactional
    public BookingResponse createBooking(CreateBookingRequest request) {
        // Validate request
        if (request.getSeatIds() == null || request.getSeatIds().isEmpty()) {
            throw new RuntimeException("At least one seat must be selected");
        }

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Show show = showRepository.findById(request.getShowId())
                .orElseThrow(() -> new RuntimeException("Show not found"));

        List<ShowSeat> showSeats = showSeatRepository.findByShowIdAndSeatIdsWithLock(
                request.getShowId(),
                request.getSeatIds()
        );

        if (showSeats.size() != request.getSeatIds().size()) {
            throw new RuntimeException("Some seats do not exist for this show");
        }

        for (ShowSeat showSeat : showSeats) {
            if (showSeat.getStatus() != SeatStatus.AVAILABLE) {
                if (showSeat.getStatus() == SeatStatus.LOCKED &&
                        showSeat.isLockExpired(LOCK_DURATION_MINUTES)) {
                    showSeat.release();
                } else {
                    throw new RuntimeException("Seat " + showSeat.getSeat().getSeatNumber() + " is not available");
                }
            }
            showSeat.lock(user.getId());
        }

        showSeatRepository.saveAll(showSeats);

        Booking booking = new Booking(user, show);

        for (ShowSeat showSeat : showSeats) {
            BookingSeat bookingSeat = new BookingSeat(booking, showSeat.getSeat(), show.getPrice());
            booking.addBookingSeat(bookingSeat);
        }

        Booking savedBooking = bookingRepository.save(booking);

        return buildBookingResponse(savedBooking);
    }

    @Transactional
    public BookingResponse confirmBooking(ConfirmBookingRequest request) {
        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (booking.getUser().getId() != request.getUserId()) {
            throw new RuntimeException("Unauthorized: This booking belongs to another user");
        }

        if (booking.getBookingStatus() != BookingStatus.CREATED) {
            throw new RuntimeException("Booking cannot be confirmed. Current status: " + booking.getBookingStatus());
        }

        List<Long> seatIds = booking.getBookingSeats().stream()
                .map(bs -> bs.getSeat().getId())
                .collect(Collectors.toList());

        List<ShowSeat> showSeats = showSeatRepository.findByShowIdAndSeatIdsWithLock(
                booking.getShow().getId(),
                seatIds
        );

        for (ShowSeat showSeat : showSeats) {
            if (!showSeat.isLockedByUser(request.getUserId())) {
                throw new RuntimeException("Seat " + showSeat.getSeat().getSeatNumber() +
                        " is not locked by this user or lock has expired");
            }
            showSeat.book();
        }

        showSeatRepository.saveAll(showSeats);

        booking.confirm();
        Booking savedBooking = bookingRepository.save(booking);

        return buildBookingResponse(savedBooking);
    }

    @Transactional
    public void cancelBooking(Long bookingId, Long userId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (booking.getUser().getId() != userId) {
            throw new RuntimeException("Unauthorized: This booking belongs to another user");
        }

        List<Long> seatIds = booking.getBookingSeats().stream()
                .map(bs -> bs.getSeat().getId())
                .collect(Collectors.toList());

        List<ShowSeat> showSeats = showSeatRepository.findByShowIdAndSeatIdsWithLock(
                booking.getShow().getId(),
                seatIds
        );

        for (ShowSeat showSeat : showSeats) {
            showSeat.release();
        }

        showSeatRepository.saveAll(showSeats);

        booking.cancel();
        bookingRepository.save(booking);
    }

    @Transactional(readOnly = true)
    public BookingResponse getBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        return buildBookingResponse(booking);
    }

    private void releaseExpiredLocks(List<ShowSeat> showSeats) {
        for (ShowSeat showSeat : showSeats) {
            if (showSeat.getStatus() == SeatStatus.LOCKED &&
                    showSeat.isLockExpired(LOCK_DURATION_MINUTES)) {
                showSeat.release();
                showSeatRepository.save(showSeat);
            }
        }
    }

    private BookingResponse buildBookingResponse(Booking booking) {
        List<BookingResponse.SeatInfo> seatInfos = booking.getBookingSeats().stream()
                .map(bs -> new BookingResponse.SeatInfo(
                        bs.getSeat().getId(),
                        bs.getSeat().getSeatNumber(),
                        bs.getPrice()
                ))
                .collect(Collectors.toList());

        return new BookingResponse(
                booking.getId(),
                booking.getUser().getId(),
                booking.getShow().getId(),
                booking.getBookingStatus(),
                seatInfos,
                booking.getTotalAmount()
        );
    }

    @Transactional(readOnly = true)
    public List<BookingResponse> getUserBookings(Long userId) {
        // Verify user exists
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Booking> bookings = bookingRepository.findByUserId(userId);

        return bookings.stream()
                .map(this::buildBookingResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BookingResponse> getUserBookingsByStatus(Long userId, BookingStatus status) {
        // Verify user exists
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Booking> bookings = bookingRepository.findByUserIdAndStatus(userId, status);

        return bookings.stream()
                .map(this::buildBookingResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BookingResponse> getUpcomingBookings(Long userId) {
        // Verify user exists
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Booking> bookings = bookingRepository.findUpcomingBookingsByUserId(userId);

        return bookings.stream()
                .map(this::buildBookingResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BookingResponse> getPastBookings(Long userId) {
        // Verify user exists
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Booking> bookings = bookingRepository.findPastBookingsByUserId(userId);

        return bookings.stream()
                .map(this::buildBookingResponse)
                .collect(Collectors.toList());
    }
}