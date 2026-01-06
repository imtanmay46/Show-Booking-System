package com.example.showcase.controller;

import com.example.showcase.dto.request.CreateTheatreRequest;
import com.example.showcase.dto.response.TheatreResponse;
import com.example.showcase.service.TheatreService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/theatres")
public class TheatreController {

    private final TheatreService theatreService;

    public TheatreController(TheatreService theatreService){
        this.theatreService = theatreService;
    }

    @PostMapping
    public TheatreResponse createTheatre(@RequestBody CreateTheatreRequest request){
        return theatreService.createTheatre(request);
    }

    @GetMapping
    public List<TheatreResponse> getAllTheatres(){
        return theatreService.getAllTheatres();
    }
}
