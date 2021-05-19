package ru.drudenko.dnd5.webapi.monster.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import ru.drudenko.dnd5.webapi.utils.StringListPersistConverter;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "tb_monsters")
public class Monster {
    @Id
    private String name;
    @Column
    private String fiction;
    @Column
    private String size;
    @Column
    private String type;
    @Column
    private String alignment;
    @Column
    private String ac;
    @Column
    private String hp;
    @Column
    private String speed;

    @Column
    private String str;
    @Column
    private String dex;
    @Column
    private String con;
    @Column(name = "int")
    private String intilect;
    @Column
    private String wis;
    @Column
    private String cha;

    @Column
    private String passive;

    @Column
    @Convert(converter = StringListPersistConverter.class)
    private List<String> languages;

    @Column
    private String cr;

    @Column(name = "cr_number")
    private Double crNumber;

    @Column
    private String senses;

    @Column
    @Convert(converter = StringListPersistConverter.class)
    private List<String> skill;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "monster")
    private List<MonsterTrait> monsterTrait = new ArrayList<>();

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "monster")
    private List<MonsterAction> monsterAction = new ArrayList<>();

    private String bioms;

    @OneToMany(mappedBy = "monster", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<MonsterFavorite> monsterFavorites = new HashSet<>();
}
