package ru.drudenko.dnd5.webapi.dto.monster;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class MonsterActionDto {
    private Long id;
    private String name;
    private String text;
    private String attack;
}
