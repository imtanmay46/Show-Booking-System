package com.example.showcase.scheduler;

import com.example.showcase.domain.enums.SeatStatus;
import com.example.showcase.domain.show.ShowSeat;
import com.example.showcase.repository.ShowSeatRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class LockCleanupScheduler {

    private static final int LOCK_DURATION_MINUTES = 10;

    private final ShowSeatRepository showSeatRepository;

    public LockCleanupScheduler(ShowSeatRepository showSeatRepository) {
        this.showSeatRepository = showSeatRepository;
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void releaseExpiredLocks() {
        LocalDateTime expiredBefore = LocalDateTime.now().minusMinutes(LOCK_DURATION_MINUTES);
        List<ShowSeat> expiredSeats = showSeatRepository.findExpiredLockedSeats(
                SeatStatus.LOCKED,
                expiredBefore
        );

        for (ShowSeat showSeat : expiredSeats) {
            showSeat.release();
        }

        if (!expiredSeats.isEmpty()) {
            showSeatRepository.saveAll(expiredSeats);
            System.out.println("Released " + expiredSeats.size() + " expired seat locks");
        }
    }
}