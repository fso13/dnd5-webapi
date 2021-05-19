package ru.drudenko.dnd5.webapi.profile.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public
interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, String> {
}
