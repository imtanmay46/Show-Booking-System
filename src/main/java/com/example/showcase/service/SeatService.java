package com.example.showcase.service;

import com.example.showcase.domain.location.Screen;
import com.example.showcase.domain.seat.Seat;
import com.example.showcase.dto.request.CreateSeatRequest;
import com.example.showcase.dto.response.SeatResponse;
import com.example.showcase.repository.ScreenRepository;
import com.example.showcase.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatService {

    private final SeatRepository seatRepository;
    private final ScreenRepository screenRepository;

    public SeatService(SeatRepository seatRepository, ScreenRepository screenRepository){
        this.seatRepository = seatRepository;
        this.screenRepository = screenRepository;
    }

    public SeatResponse createSeat(CreateSeatRequest request){
        Screen screen = screenRepository.findById(request.getScreenId()).orElseThrow(()-> new RuntimeException("Screen Not Found!"));

        Seat seat = new Seat(request.getSeatNumber(), request.getSeatType(), screen);
        Seat saved = seatRepository.save(seat);

        return new SeatResponse(saved.getId(), saved.getSeatNumber(), saved.getSeatType(), saved.getScreen().getId());
    }

    public List<SeatResponse> getAllSeats(){
        return seatRepository.findAll().stream().map(seat -> new SeatResponse(seat.getId(), seat.getSeatNumber(), seat.getSeatType(), seat.getScreen().getId())).toList();
    }
}
