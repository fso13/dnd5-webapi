package ru.drudenko.dnd5.webapi.dto.profile;

import lombok.Data;

@Data
public class ProfileDto {
    private String id;
    private String name;
    private boolean isCurrent;
}
