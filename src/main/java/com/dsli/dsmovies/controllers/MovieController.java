package com.dsli.dsmovies.controllers;

import com.dsli.dsmovies.dtos.ResponseAPI;
import com.dsli.dsmovies.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public ResponseEntity<Object> findAllMoviesPaged(Pageable pageable) {
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ResponseAPI.getInstance(Collections
                            .singletonList(movieService.findAllPaged(pageable))));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseAPI.getInstance(String.format("Error to find movies >> %s", ex.getMessage()),
                            Arrays.stream(ex.getSuppressed()).map(Throwable::getMessage)
                                    .toArray(String[]::new)));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findMovieById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ResponseAPI.getInstance(Collections
                            .singletonList(movieService.findMovieById(id))));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseAPI.getInstance(String.format("Error to find movies >> %s", ex.getMessage()),
                            Arrays.stream(ex.getSuppressed()).map(Throwable::getMessage)
                                    .toArray(String[]::new)));
        }
    }
}
