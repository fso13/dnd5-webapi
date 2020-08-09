package ru.drudenko.dnd5.webapi.impl.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class PasswordResetTokenRepositoryImpl implements PasswordResetTokenRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Optional<PasswordResetToken> findById(String id) {
        return Optional.ofNullable(entityManager.find(PasswordResetToken.class, id));
    }

    @Override
    public PasswordResetToken save(PasswordResetToken passwordResetToken) {
        entityManager.persist(passwordResetToken);
        return passwordResetToken;
    }
}
