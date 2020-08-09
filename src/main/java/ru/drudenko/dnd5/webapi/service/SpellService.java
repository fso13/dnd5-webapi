package ru.drudenko.dnd5.webapi.service;

import org.springframework.data.domain.Page;
import ru.drudenko.dnd5.webapi.dto.common.FavoriteDto;
import ru.drudenko.dnd5.webapi.dto.spell.SpellDto;
import ru.drudenko.dnd5.webapi.dto.spell.SpellSearchDto;

public interface SpellService {

    SpellDto getByName(String name);

    Page<SpellDto> search(SpellSearchDto filter);

    void setFavorite(String name, FavoriteDto favoriteDto);
}
