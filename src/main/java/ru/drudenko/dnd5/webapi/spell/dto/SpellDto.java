package ru.drudenko.dnd5.webapi.spell.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class SpellDto {
    private String nameEn;
    private String name;
    private String school;
    private String level;
    private String castingTime;
    private String range;
    private String components;
    private String duration;
    private String text;
    private Set<SpellClassDto> spellClass = new HashSet<>();
    private Boolean isFavorite;


    public String printSpellClasses() {
        return spellClass.stream().map(SpellClassDto::getName).collect(Collectors.joining(", "));
    }
}
