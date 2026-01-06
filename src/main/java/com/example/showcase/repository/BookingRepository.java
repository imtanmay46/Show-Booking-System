package com.example.showcase.repository;

import com.example.showcase.domain.booking.Booking;
import com.example.showcase.domain.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b WHERE b.user.id = :userId ORDER BY b.createdAt DESC")
    List<Booking> findByUserId(@Param("userId") Long userId);

    @Query("SELECT b FROM Booking b WHERE b.user.id = :userId AND b.bookingStatus = :status ORDER BY b.createdAt DESC")
    List<Booking> findByUserIdAndStatus(@Param("userId") Long userId, @Param("status") BookingStatus status);

    @Query("SELECT b FROM Booking b WHERE b.user.id = :userId AND b.show.startTime > CURRENT_TIMESTAMP AND b.bookingStatus = 'CONFIRMED' ORDER BY b.show.startTime ASC")
    List<Booking> findUpcomingBookingsByUserId(@Param("userId") Long userId);

    @Query("SELECT b FROM Booking b WHERE b.user.id = :userId AND b.show.startTime < CURRENT_TIMESTAMP ORDER BY b.show.startTime DESC")
    List<Booking> findPastBookingsByUserId(@Param("userId") Long userId);
}
