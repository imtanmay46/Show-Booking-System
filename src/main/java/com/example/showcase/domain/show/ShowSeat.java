package com.example.showcase.domain.show;

import com.example.showcase.domain.common.BaseEntity;
import com.example.showcase.domain.enums.SeatStatus;
import com.example.showcase.domain.seat.Seat;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "show_seats", uniqueConstraints = {@UniqueConstraint(columnNames = {"show_id", "seat_id"})})
public class ShowSeat extends BaseEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "show_id")
    private Show show;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatStatus status;

    @Column(name = "locked_at")
    private LocalDateTime lockedAt;

    @Column(name = "locked_by_user_id")
    private Long lockedByUserId;

    @Version
    private Long version;

    protected ShowSeat() {}

    public ShowSeat(Show show, Seat seat) {
        this.show = show;
        this.seat = seat;
        this.status = SeatStatus.AVAILABLE;
    }

    public void lock(Long userId) {
        if (this.status != SeatStatus.AVAILABLE) {
            throw new IllegalStateException("Seat is not available for locking");
        }
        this.status = SeatStatus.LOCKED;
        this.lockedAt = LocalDateTime.now();
        this.lockedByUserId = userId;
    }

    public void book() {
        if (this.status != SeatStatus.LOCKED) {
            throw new IllegalStateException("Seat must be locked before booking");
        }
        this.status = SeatStatus.BOOKED;
    }

    public void release() {
        this.status = SeatStatus.AVAILABLE;
        this.lockedAt = null;
        this.lockedByUserId = null;
    }

    public boolean isLockedByUser(Long userId) {
        return this.status == SeatStatus.LOCKED &&
                this.lockedByUserId != null &&
                this.lockedByUserId.equals(userId);
    }

    public boolean isLockExpired(int lockDurationMinutes) {
        if (this.status != SeatStatus.LOCKED || this.lockedAt == null) {
            return false;
        }
        return LocalDateTime.now().isAfter(this.lockedAt.plusMinutes(lockDurationMinutes));
    }

    public Show getShow() { return show; }
    public Seat getSeat() { return seat; }
    public SeatStatus getStatus() { return status; }
    public LocalDateTime getLockedAt() { return lockedAt; }
    public Long getLockedByUserId() { return lockedByUserId; }
}