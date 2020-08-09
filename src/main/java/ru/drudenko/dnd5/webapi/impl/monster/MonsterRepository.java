package ru.drudenko.dnd5.webapi.impl.monster;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

@Transactional
interface MonsterRepository {
    Monster getOne(String name);

    Page<Monster> findAll(Specification<Monster> monsterSpecification, Pageable pageRequest);
}
