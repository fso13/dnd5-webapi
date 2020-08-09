package ru.drudenko.dnd5.webapi.service;

import org.springframework.data.domain.Page;
import ru.drudenko.dnd5.webapi.dto.common.FavoriteDto;
import ru.drudenko.dnd5.webapi.dto.monster.MonsterDto;
import ru.drudenko.dnd5.webapi.dto.monster.MonsterSearchDto;

public interface MonsterService {

    MonsterDto getByName(String name);

    Page<MonsterDto> search(MonsterSearchDto filter);

    void setFavorite(String name, FavoriteDto favoriteDto);

}
