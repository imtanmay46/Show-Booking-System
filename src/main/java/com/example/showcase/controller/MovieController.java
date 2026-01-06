package com.example.showcase.controller;

import com.example.showcase.domain.movie.Movie;
import com.example.showcase.dto.request.CreateMovieRequest;
import com.example.showcase.dto.response.MovieResponse;
import com.example.showcase.service.MovieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService){
        this.movieService = movieService;
    }

    @PostMapping
    public MovieResponse createMovie(@RequestBody CreateMovieRequest request){
        return movieService.createMovie(request);
    }

    @GetMapping
    public List<MovieResponse> getAllMovies(){
        return movieService.getAllMovies();
    }
}
