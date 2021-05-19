package ru.drudenko.dnd5.webapi.monster.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface MonsterRepository extends JpaRepository<Monster, String>, JpaSpecificationExecutor<Monster> {

}
