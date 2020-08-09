package ru.drudenko.dnd5.webapi.impl.spell;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
interface SpellRepository {

    Optional<Spell> findByNameOrNameEn(String name);

    Page<Spell> findAll(Specification<Spell> spellSpecification, Pageable pageRequest);
}
