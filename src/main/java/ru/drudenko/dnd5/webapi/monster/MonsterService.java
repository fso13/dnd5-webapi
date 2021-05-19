package ru.drudenko.dnd5.webapi.monster;

import org.springframework.data.domain.Page;
import ru.drudenko.dnd5.webapi.common.dto.FavoriteDto;
import ru.drudenko.dnd5.webapi.monster.dto.MonsterDto;
import ru.drudenko.dnd5.webapi.monster.dto.MonsterSearchDto;

public interface MonsterService {

    MonsterDto getByName(String name);

    Page<MonsterDto> search(MonsterSearchDto filter);

    void setFavorite(String name, FavoriteDto favoriteDto);

}
