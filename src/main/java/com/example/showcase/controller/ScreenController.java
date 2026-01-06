package com.example.showcase.controller;

import com.example.showcase.dto.request.CreateScreenRequest;
import com.example.showcase.dto.response.ScreenResponse;
import com.example.showcase.service.ScreenService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/screens")
public class ScreenController {

    private final ScreenService screenService;

    public ScreenController(ScreenService screenService){
        this.screenService = screenService;
    }

    @PostMapping
    public ScreenResponse createScreen(@RequestBody CreateScreenRequest request){
        return screenService.createScreen(request);
    }

    @GetMapping
    public List<ScreenResponse> getAllScreens(){
        return screenService.getAllScreens();
    }
}
