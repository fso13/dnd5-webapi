package ru.drudenko.dnd5.webapi.rating.dto;

import lombok.Data;


@Data
public class RatingDto {
    private String nick;
    private int quest;
    private int xp;
}
