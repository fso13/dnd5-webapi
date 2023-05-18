package ru.drudenko.dnd5.webapi.spell.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import ru.drudenko.dnd5.webapi.spell.dto.SpellDto;
import ru.drudenko.dnd5.webapi.spell.model.Spell;

@Mapper
public
interface SpellMapper {

    SpellMapper MAPPER = Mappers.getMapper(SpellMapper.class);

    @Mappings({
            @Mapping(expression = "java(org.springframework.util.StringUtils.capitalize(spell.getName().toLowerCase()))", target = "name"),
            @Mapping(expression = "java(org.springframework.util.StringUtils.capitalize(java.util.Optional.ofNullable(spell.getNameEn()).orElse(\"\").toLowerCase()))", target = "nameEn"),
    })
    SpellDto fromEntity(Spell spell);
}
