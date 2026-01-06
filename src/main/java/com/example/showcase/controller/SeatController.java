package com.example.showcase.controller;

import com.example.showcase.domain.seat.Seat;
import com.example.showcase.dto.request.CreateSeatRequest;
import com.example.showcase.dto.response.SeatResponse;
import com.example.showcase.service.SeatService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
public class SeatController {

    private final SeatService seatService;

    public SeatController(SeatService seatService){
        this.seatService = seatService;
    }

    @PostMapping
    public SeatResponse createSeat(@RequestBody CreateSeatRequest request){
        return seatService.createSeat(request);
    }

    @GetMapping
    public List<SeatResponse> getAllSeats(){
        return seatService.getAllSeats();
    }
}
