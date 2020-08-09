package ru.drudenko.dnd5.webapi.impl.user;

import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
interface PasswordResetTokenRepository {
    Optional<PasswordResetToken> findById(String id);

    PasswordResetToken save(PasswordResetToken passwordResetToken);
}
