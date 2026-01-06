package com.example.showcase.controller;

import com.example.showcase.domain.location.City;
import com.example.showcase.dto.request.CreateCityRequest;
import com.example.showcase.dto.response.CityResponse;
import com.example.showcase.service.CityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService){
        this.cityService = cityService;
    }

    @PostMapping
    public CityResponse createCity(@RequestBody CreateCityRequest request){
        return cityService.createCity(request);
    }

    @GetMapping
    public List<CityResponse> getAllCities(){
        return cityService.getAllCities();
    }
}
