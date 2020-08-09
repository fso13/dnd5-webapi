package ru.drudenko.dnd5.webapi.dto.monster;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonsterTraitDto {
    private Long id;
    private String name;
    private String text;
    private String attack;
}
