package com.dsli.dsmovies.controllers;

import com.dsli.dsmovies.dtos.MovieDto;
import com.dsli.dsmovies.dtos.ResponseAPI;
import com.dsli.dsmovies.dtos.ScoreDto;
import com.dsli.dsmovies.services.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;

@RestController
@RequestMapping("scores")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @PutMapping
    public ResponseEntity<Object> saveScore(@RequestBody ScoreDto scoreDto) {
        try {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseAPI.getInstance(Collections
                        .singletonList(scoreService.saveScore(scoreDto))));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseAPI.getInstance(String.format("Error to save score >> %s", ex.getMessage()),
                            Arrays.stream(ex.getSuppressed()).map(Throwable::getMessage)
                                    .toArray(String[]::new)));
        }
    }
}
