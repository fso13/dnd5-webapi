package ru.drudenko.dnd5.webapi.dto.monster;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MonsterActionDto {
    private Long id;
    private String name;
    private String text;
    private String attack;
}
