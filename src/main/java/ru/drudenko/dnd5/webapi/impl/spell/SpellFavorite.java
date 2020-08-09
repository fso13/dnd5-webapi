package ru.drudenko.dnd5.webapi.impl.spell;

import lombok.Getter;
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
@Table(name = "tb_spell_favorite")
class SpellFavorite {

    @Column(name = "favorite")
    Boolean isFavorite;

    @EmbeddedId
    private SpellFavoriteId spellFavoriteId;

    @ManyToOne
    @JoinColumn(name = "spell_id", insertable = false, updatable = false)
    private Spell spell;
}
