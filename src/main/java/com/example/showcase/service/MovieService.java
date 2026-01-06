package com.example.showcase.service;

import com.example.showcase.domain.movie.Movie;
import com.example.showcase.dto.request.CreateMovieRequest;
import com.example.showcase.dto.response.MovieResponse;
import com.example.showcase.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    public MovieResponse createMovie(CreateMovieRequest request){

        Movie movie = new Movie(request.getTitle(), request.getDurationInMinutes(), request.getLanguage());
        Movie saved = movieRepository.save(movie);

        return new MovieResponse(saved.getId(), saved.getTitle(), saved.getLanguage(), saved.getDurationInMinutes());
    }

    public List<MovieResponse> getAllMovies(){
        return movieRepository.findAll().stream().map(movie -> new MovieResponse(movie.getId(), movie.getTitle(), movie.getLanguage(), movie.getDurationInMinutes())).toList();
    }
}
