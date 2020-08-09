package ru.drudenko.dnd5.webapi.impl.spell;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "tb_spells")
class Spell {
    @Id
    private String name;
    @Column(name = "name_en")
    private String nameEn;
    @Column
    private String school;
    @Column
    private String level;
    @Column(name = "casting_time")
    private String castingTime;
    @Column
    private String range;
    @Column(length = 1000)
    private String components;
    @Column
    private String duration;
    @Column(length = 6000)
    private String text;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "tb_spell_class_spells", joinColumns = @JoinColumn(name = "spells_name"), inverseJoinColumns = @JoinColumn(name = "spell_class_name"))
    @OrderBy("name")
    private Set<SpellClass> spellClass = new HashSet<>();

    @OneToMany(mappedBy = "spell", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<SpellFavorite> spellFavorites = new HashSet<>();
}
