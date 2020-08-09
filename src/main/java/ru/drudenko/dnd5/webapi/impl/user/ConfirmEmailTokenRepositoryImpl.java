package ru.drudenko.dnd5.webapi.impl.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConfirmEmailTokenRepositoryImpl implements ConfirmEmailTokenRepository {

    @PersistenceContext
    private final EntityManager entityManager;


    @Override
    public Optional<ConfirmEmailToken> findById(String id) {
        return Optional.ofNullable(entityManager.find(ConfirmEmailToken.class, id));
    }

    @Override
    public ConfirmEmailToken save(ConfirmEmailToken confirmEmailToken) {
        entityManager.persist(confirmEmailToken);
        return confirmEmailToken;
    }
}
