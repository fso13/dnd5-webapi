package ru.drudenko.dnd5.webapi.impl.monster;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@RequiredArgsConstructor
@Service
class MonsterFavoriteRepositoryImpl implements MonsterFavoriteRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Optional<MonsterFavorite> findById(MonsterFavoriteId monsterFavoriteId) {
        try {
            return Optional.ofNullable(entityManager.createQuery("Select m from MonsterFavorite m where m.monsterFavoriteId = :id", MonsterFavorite.class)
                    .setParameter("id", monsterFavoriteId)
//                    .setHint("org.hibernate.cacheable", true)
                    .getSingleResult());
        } catch (NoResultException exception) {
            return Optional.empty();
        }
    }

    @Override
    public MonsterFavorite save(MonsterFavorite monsterFavorite) {
        entityManager.persist(monsterFavorite);
        return monsterFavorite;
    }
}
