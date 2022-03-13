package com.dsli.dsmovies.services;

import com.dsli.dsmovies.dtos.MovieDto;
import com.dsli.dsmovies.dtos.ScoreDto;
import com.dsli.dsmovies.models.Movie;
import com.dsli.dsmovies.models.Score;
import com.dsli.dsmovies.models.User;
import com.dsli.dsmovies.repositories.MovieRepository;
import com.dsli.dsmovies.repositories.ScoreRepository;
import com.dsli.dsmovies.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ScoreService {

    @Autowired
    private ScoreRepository scoreRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MovieRepository movieRepository;

    @Transactional
    public MovieDto saveScore(ScoreDto scoreDto) {

        User user = userRepository.findByEmail(scoreDto.getEmail());
        if (user == null) {
            user = new User();
            user.setEmail(scoreDto.getEmail());
            user = userRepository.saveAndFlush(user);
        }

        Movie movie = movieRepository.findById(scoreDto.getMovieId()).get();

        Score score = new Score();
        score.setMovie(movie);
        score.setUser(user);
        score.setValue(scoreDto.getScore());

        score = scoreRepository.saveAndFlush(score);

        double sum = 0.0;

        for (Score s : movie.getScores()) {
            sum = sum + s.getValue();
        }

        double avg = sum / movie.getScores().size();

        movie.setScore(avg);
        movie.setCount(movie.getScores().size());

        return new MovieDto(movie);
    }
}
