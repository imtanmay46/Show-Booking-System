package com.example.showcase.repository;

import com.example.showcase.domain.enums.SeatStatus;
import com.example.showcase.domain.show.ShowSeat;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT ss FROM ShowSeat ss WHERE ss.show.id = :showId AND ss.seat.id IN :seatIds")
    List<ShowSeat> findByShowIdAndSeatIdsWithLock(@Param("showId") Long showId, @Param("seatIds") List<Long> seatIds);

    @Query("SELECT ss FROM ShowSeat ss WHERE ss.show.id = :showId")
    List<ShowSeat> findByShowId(@Param("showId") Long showId);

    @Query("SELECT ss FROM ShowSeat ss WHERE ss.status = :status AND ss.lockedAt < :expiredBefore")
    List<ShowSeat> findExpiredLockedSeats(@Param("status") SeatStatus status, @Param("expiredBefore") LocalDateTime expiredBefore);

    boolean existsByShowIdAndSeatId(Long showId, Long seatId);
}