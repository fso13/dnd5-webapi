package ru.drudenko.dnd5.webapi.spell.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Repository
public
interface SpellRepository extends JpaRepository<Spell, String>, JpaSpecificationExecutor<Spell> {

    Optional<Spell> findByNameIgnoreCaseOrNameEnIgnoreCase(String name, String nameEn);
}
