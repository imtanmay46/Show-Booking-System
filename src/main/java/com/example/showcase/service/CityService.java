package com.example.showcase.service;

import com.example.showcase.domain.location.City;
import com.example.showcase.dto.request.CreateCityRequest;
import com.example.showcase.dto.response.CityResponse;
import com.example.showcase.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository){
        this.cityRepository = cityRepository;
    }

    public CityResponse createCity(CreateCityRequest request){
        City city = new City(request.getName());
        City savedCity = cityRepository.save(city);

        return new CityResponse(savedCity.getId(), savedCity.getName());
    }

    public List<CityResponse> getAllCities(){
        return cityRepository.findAll().stream().map(city -> new CityResponse(city.getId(), city.getName())).toList();
    }
}
