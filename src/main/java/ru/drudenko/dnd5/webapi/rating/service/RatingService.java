package ru.drudenko.dnd5.webapi.rating.service;

import ru.drudenko.dnd5.webapi.rating.dto.RatingDto;

import java.util.List;

public interface RatingService {
    List<RatingDto> getAll();

    void create(RatingDto dto);
}
