package ru.drudenko.dnd5.webapi.spell;

import org.springframework.data.domain.Page;
import ru.drudenko.dnd5.webapi.common.dto.FavoriteDto;
import ru.drudenko.dnd5.webapi.spell.dto.SpellDto;
import ru.drudenko.dnd5.webapi.spell.dto.SpellSearchDto;

public interface SpellService {

    SpellDto getByName(String name);

    Page<SpellDto> search(SpellSearchDto filter);

    void setFavorite(String name, FavoriteDto favoriteDto);
}
