package ru.drudenko.dnd5.webapi.rating.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.drudenko.dnd5.webapi.rating.dto.RatingDto;
import ru.drudenko.dnd5.webapi.rating.mapper.RatingMapper;
import ru.drudenko.dnd5.webapi.rating.model.Rating;
import ru.drudenko.dnd5.webapi.rating.model.RatingRepository;
import ru.drudenko.dnd5.webapi.rating.service.RatingService;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    @Override
    public List<RatingDto> getAll() {
        return ratingRepository.findAll().parallelStream().map(RatingMapper.MAPPER::fromEntity).collect(Collectors.toList());
    }

    @Override
    public void create(RatingDto dto) {
        Rating rating = RatingMapper.MAPPER.toEntity(dto);
        ratingRepository.saveAndFlush(rating);
    }
}
