package com.dsli.dsmovies.services;

import com.dsli.dsmovies.dtos.MovieDto;
import com.dsli.dsmovies.models.Movie;
import com.dsli.dsmovies.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Transactional(readOnly = true)
    public Page<MovieDto> findAllPaged(Pageable pageable) {

        Page<Movie> allMovies = movieRepository.findAll(pageable);
        Page<MovieDto> result = allMovies.map(MovieDto::new);

        return result;
    }

    @Transactional(readOnly = true)
    public MovieDto findMovieById(Long id) {
        Objects.requireNonNull(id, "Id must not be null");
        Optional<Movie> movie = movieRepository.findById(id);

        if (movie.isPresent()) {
            MovieDto result = new MovieDto(movie.get());
            return result;
        }
        return null;
    }
}
