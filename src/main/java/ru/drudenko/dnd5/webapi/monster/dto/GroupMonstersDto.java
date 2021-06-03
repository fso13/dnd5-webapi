package ru.drudenko.dnd5.webapi.monster.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupMonstersDto {
    List<MonsterDto> firstTwo = new ArrayList<>();
    List<MonsterDto> nextTwo = new ArrayList<>();
}
