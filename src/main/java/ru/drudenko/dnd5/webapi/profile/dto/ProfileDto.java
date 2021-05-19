package ru.drudenko.dnd5.webapi.profile.dto;

import lombok.Data;

@Data
public class ProfileDto {
    private String id;
    private String name;
    private boolean isCurrent;
}
