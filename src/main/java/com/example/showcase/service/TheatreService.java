package com.example.showcase.service;

import com.example.showcase.domain.location.City;
import com.example.showcase.domain.location.Theatre;
import com.example.showcase.dto.request.CreateTheatreRequest;
import com.example.showcase.dto.response.TheatreResponse;
import com.example.showcase.repository.CityRepository;
import com.example.showcase.repository.TheatreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TheatreService {

    private final TheatreRepository theatreRepository;
    private final CityRepository cityRepository;

    public TheatreService(TheatreRepository theatreRepository, CityRepository cityRepository){
        this.theatreRepository = theatreRepository;
        this.cityRepository = cityRepository;
    }

    public TheatreResponse createTheatre(CreateTheatreRequest request){
        City city = cityRepository.findById(request.getCityId()).orElseThrow(()-> new RuntimeException("City Not Found!"));

        Theatre theatre = new Theatre(request.getName(), city);
        Theatre saved = theatreRepository.save(theatre);

        return new TheatreResponse(saved.getId(), saved.getName(), city.getId(), city.getName());
    }

    public List<TheatreResponse> getAllTheatres(){
        return theatreRepository.findAll().stream().map(theatre -> new TheatreResponse(theatre.getId(), theatre.getName(), theatre.getCity().getId(), theatre.getCity().getName())).toList();
    }
}
