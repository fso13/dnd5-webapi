package ru.drudenko.dnd5.webapi.dto.common;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
public class AttributeDto {
    private String name;
    private boolean selected;
}
