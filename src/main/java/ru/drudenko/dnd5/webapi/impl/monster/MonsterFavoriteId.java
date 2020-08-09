package ru.drudenko.dnd5.webapi.impl.monster;

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
public class MonsterFavoriteId implements Serializable {
    @Column(name = "profile_id")
    private String profileId;

    @Column(name = "monster_id")
    private String monsterId;
}
