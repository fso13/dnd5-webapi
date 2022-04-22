package ru.drudenko.dnd5.webapi.rating.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import ru.drudenko.dnd5.webapi.rating.dto.RatingDto;
import ru.drudenko.dnd5.webapi.rating.model.Rating;
import ru.drudenko.dnd5.webapi.spell.dto.SpellDto;
import ru.drudenko.dnd5.webapi.spell.model.Spell;

@Mapper
public interface RatingMapper {

    RatingMapper MAPPER = Mappers.getMapper(RatingMapper.class);

    RatingDto fromEntity(Rating rating);

    Rating toEntity(RatingDto rating);
}
