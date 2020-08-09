package ru.drudenko.dnd5.webapi.impl.spell;

import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
interface SpellFavoriteRepository {
    Optional<SpellFavorite> findById(SpellFavoriteId spellFavoriteId);

    SpellFavorite save(SpellFavorite spellFavorite);
}
