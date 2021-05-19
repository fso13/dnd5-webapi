package ru.drudenko.dnd5.webapi.spell.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpellFavoriteId implements Serializable {
    @Column(name = "profile_id")
    private String profileId;

    @Column(name = "spell_id")
    private String spellId;
}
