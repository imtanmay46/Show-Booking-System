package com.example.showcase.controller;

import com.example.showcase.dto.request.CreateShowRequest;
import com.example.showcase.dto.response.ShowResponse;
import com.example.showcase.service.ShowService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shows")
public class ShowController {

    private final ShowService showService;

    public ShowController(ShowService showService){
        this.showService = showService;
    }

    @PostMapping
    public ShowResponse createShow(@RequestBody CreateShowRequest request){
        return showService.createShow(request);
    }

    @GetMapping
    public List<ShowResponse> getAllShows(){
        return showService.getAllShows();
    }
}
