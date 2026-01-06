package com.example.showcase.service;

import com.example.showcase.domain.location.Screen;
import com.example.showcase.domain.movie.Movie;
import com.example.showcase.domain.seat.Seat;
import com.example.showcase.domain.show.Show;
import com.example.showcase.domain.show.ShowSeat;
import com.example.showcase.dto.request.CreateShowRequest;
import com.example.showcase.dto.response.ShowResponse;
import com.example.showcase.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ShowService {

    private final ShowRepository showRepository;
    private final MovieRepository movieRepository;
    private final ScreenRepository screenRepository;
    private final SeatRepository seatRepository;
    private final ShowSeatRepository showSeatRepository;

    public ShowService(
            ShowRepository showRepository,
            MovieRepository movieRepository,
            ScreenRepository screenRepository,
            SeatRepository seatRepository,
            ShowSeatRepository showSeatRepository) {
        this.showRepository = showRepository;
        this.movieRepository = movieRepository;
        this.screenRepository = screenRepository;
        this.seatRepository = seatRepository;
        this.showSeatRepository = showSeatRepository;
    }

    @Transactional
    public ShowResponse createShow(CreateShowRequest request) {
        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        Screen screen = screenRepository.findById(request.getScreenId())
                .orElseThrow(() -> new RuntimeException("Screen not found"));

        Show show = new Show(movie, screen, request.getStartTime(), request.getPrice());
        Show savedShow = showRepository.save(show);

        List<Seat> seats = seatRepository.findAll().stream()
                .filter(seat -> seat.getScreen().getId() == screen.getId())
                .toList();

        List<ShowSeat> showSeats = seats.stream()
                .map(seat -> new ShowSeat(savedShow, seat))
                .toList();

        showSeatRepository.saveAll(showSeats);

        return new ShowResponse(
                savedShow.getId(),
                savedShow.getMovie().getId(),
                savedShow.getScreen().getId(),
                savedShow.getStartTime(),
                savedShow.getPrice()
        );
    }

    public List<ShowResponse> getAllShows() {
        return showRepository.findAll().stream()
                .map(show -> new ShowResponse(
                        show.getId(),
                        show.getMovie().getId(),
                        show.getScreen().getId(),
                        show.getStartTime(),
                        show.getPrice()
                ))
                .toList();
    }
}