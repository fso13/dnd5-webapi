package ru.drudenko.dnd5.webapi.rating.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class RatingDto {
    private String nick;
    private int quest;
    private int xp;
    private String attack;
    private Instant date;
}
