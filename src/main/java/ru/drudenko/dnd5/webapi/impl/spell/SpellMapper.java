package ru.drudenko.dnd5.webapi.impl.spell;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import ru.drudenko.dnd5.webapi.dto.spell.SpellClassDto;
import ru.drudenko.dnd5.webapi.dto.spell.SpellDto;

@Mapper
interface SpellMapper {

    SpellMapper MAPPER = Mappers.getMapper(SpellMapper.class);

    @Mappings({
            @Mapping(expression = "java(org.springframework.util.StringUtils.capitalize(spell.getName().toLowerCase()))", target = "name"),
    })
    SpellDto fromEntity(Spell spell);

    SpellClassDto fromEntity(SpellClass spellClass);

}
