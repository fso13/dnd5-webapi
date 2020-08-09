package ru.drudenko.dnd5.webapi.impl.monster;

import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
interface MonsterFavoriteRepository {
    Optional<MonsterFavorite> findById(MonsterFavoriteId monsterFavoriteId);

    MonsterFavorite save(MonsterFavorite monsterFavorite);
}
