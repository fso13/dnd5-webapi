package ru.drudenko.dnd5.webapi.impl.monster;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SequenceGenerator(name = "create_seq", sequenceName = "seq_entity", allocationSize = 1)
@Table(name = "tb_actions")
class MonsterAction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "create_seq")
    private Long id;
    @Column
    private String name;
    @ManyToOne
    private Monster monster;
    @Column
    private String text;
    @Column
    private String attack;
}
