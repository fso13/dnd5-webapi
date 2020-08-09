package ru.drudenko.dnd5.webapi.impl.spell;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.drudenko.dnd5.webapi.utils.RepositoryUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Slf4j
@Service
class SpellRepositoryImpl extends RepositoryUtils<Spell> implements SpellRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    SpellRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Spell.class);
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Spell> findByNameOrNameEn(String name) {
        try {
            return Optional.of((Spell) entityManager.createQuery("select s from Spell s where s.name = :name OR s.nameEn = :name")
                    .setParameter("name", name)
//                    .setHint("org.hibernate.cacheable", true)
                    .getSingleResult());
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            return Optional.empty();
        }
    }
}
