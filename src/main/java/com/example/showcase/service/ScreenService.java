package com.example.showcase.service;

import com.example.showcase.domain.location.Screen;
import com.example.showcase.domain.location.Theatre;
import com.example.showcase.dto.request.CreateScreenRequest;
import com.example.showcase.dto.response.ScreenResponse;
import com.example.showcase.repository.ScreenRepository;
import com.example.showcase.repository.TheatreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ScreenService {

    private final ScreenRepository screenRepository;
    private final TheatreRepository theatreRepository;

    public ScreenService(ScreenRepository screenRepository, TheatreRepository theatreRepository){
        this.screenRepository = screenRepository;
        this.theatreRepository = theatreRepository;
    }

    public ScreenResponse createScreen(CreateScreenRequest request){

        Theatre theatre = theatreRepository.findById(request.getTheatreId()).orElseThrow(()-> new RuntimeException("Theatre Not Found!"));

        Screen screen = new Screen(request.getName(), request.getTotalSeats(), theatre);
        Screen saved = screenRepository.save(screen);

        return new ScreenResponse(saved.getId(), saved.getName(), saved.getTotalSeats(), theatre.getId(), theatre.getName());
    }

    @Transactional(readOnly = true)
    public List<ScreenResponse> getAllScreens(){
        return screenRepository.findAll().stream().map(screen -> new ScreenResponse(screen.getId(), screen.getName(), screen.getTotalSeats(), screen.getTheatre().getId(), screen.getTheatre().getName())).toList();
    }
}
