package ru.drudenko.dnd5.webapi.impl.spell;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class SpellFavoriteRepositoryImpl implements SpellFavoriteRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Optional<SpellFavorite> findById(SpellFavoriteId spellFavoriteId) {
        try {
            return Optional.ofNullable(entityManager.createQuery("Select s from SpellFavorite s where s.spellFavoriteId = :id", SpellFavorite.class)
                    .setParameter("id", spellFavoriteId)
                    .setHint("org.hibernate.cacheable", true)
                    .getSingleResult());
        } catch (NoResultException exception) {
            return Optional.empty();
        }
    }

    @Override
    public SpellFavorite save(SpellFavorite spellFavorite) {
        entityManager.persist(spellFavorite);
        return spellFavorite;
    }
}
