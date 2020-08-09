package ru.drudenko.dnd5.webapi.impl.monster;

import org.springframework.stereotype.Service;
import ru.drudenko.dnd5.webapi.utils.RepositoryUtils;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Service
class MonsterRepositoryImpl extends RepositoryUtils<Monster> implements MonsterRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    MonsterRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Monster.class);
        this.entityManager = entityManager;
    }


    @Override
    public Monster getOne(String name) {
        try {
            return entityManager.createQuery("Select m from Monster m where m.name = :name", Monster.class)
                    .setParameter("name", name)
                    .setHint("org.hibernate.cacheable", true)
                    .getSingleResult();
        } catch (NoResultException exception) {
            return null;
        }
    }
}
