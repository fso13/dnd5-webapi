package ru.drudenko.dnd5.webapi.impl.monster;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_monster_favorite")
class MonsterFavorite {

    @Column(name = "favorite")
    private Boolean isFavorite;

    @EmbeddedId
    private MonsterFavoriteId monsterFavoriteId;

    @ManyToOne
    @JoinColumn(name = "monster_id", insertable = false, updatable = false)
    private Monster monster;
}
