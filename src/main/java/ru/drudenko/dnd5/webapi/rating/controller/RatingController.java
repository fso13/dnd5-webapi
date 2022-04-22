package ru.drudenko.dnd5.webapi.rating.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.drudenko.dnd5.webapi.rating.dto.RatingDto;
import ru.drudenko.dnd5.webapi.rating.service.RatingService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/rating", produces = MediaType.APPLICATION_JSON_VALUE)
public class RatingController {

    private final RatingService ratingService;
    @GetMapping
    public List<RatingDto> getAll() {
        return ratingService.getAll();
    }

    @PostMapping
    public void create(RatingDto dto) {
        ratingService.create(dto);
    }
}
