package ru.drudenko.dnd5.webapi.spell.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface SpellFavoriteRepository extends JpaRepository<SpellFavorite, SpellFavoriteId> {
}
