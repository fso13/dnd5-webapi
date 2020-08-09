package ru.drudenko.dnd5.webapi.impl.user;

import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
interface ConfirmEmailTokenRepository {
    Optional<ConfirmEmailToken> findById(String id);

    ConfirmEmailToken save(ConfirmEmailToken confirmEmailToken);
}
